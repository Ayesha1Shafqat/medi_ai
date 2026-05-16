package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddTaskActivity extends AppCompatActivity {

    private EditText etTaskName, etDescription;
    private Button btnSaveTask;
    private ImageButton btnBack;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mAuth = FirebaseAuth.getInstance();

        // ✅ FIXED ROOT NODE
        mDatabase = FirebaseDatabase.getInstance()
                .getReference("Tasks");

        etTaskName = findViewById(R.id.etTaskName);
        etDescription = findViewById(R.id.etDescription);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnSaveTask.setOnClickListener(v -> saveTaskToFirebase());
    }

    private void saveTaskToFirebase() {

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Please Login First!", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskName = etTaskName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(taskName)) {
            etTaskName.setError("Task name is required");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            etDescription.setError("Description is required");
            return;
        }

        String userId = currentUser.getUid();

        // ✅ FIXED STRUCTURE
        DatabaseReference userTaskRef = mDatabase.child(userId);

        String taskId = userTaskRef.push().getKey();

        if (taskId == null) {
            Toast.makeText(this, "Task ID error", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> taskMap = new HashMap<>();
        taskMap.put("taskId", taskId);
        taskMap.put("taskName", taskName);
        taskMap.put("description", description);
        taskMap.put("completed", false);
        taskMap.put("timestamp", System.currentTimeMillis());

        userTaskRef.child(taskId).setValue(taskMap)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(this, "Task Added Successfully!", Toast.LENGTH_SHORT).show();

                    etTaskName.setText("");
                    etDescription.setText("");

                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
