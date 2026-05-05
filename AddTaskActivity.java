package com.example.medi_ai;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {

    MaterialAutoCompleteTextView priorityDropdown;
    android.widget.EditText taskNameInput, taskDescInput, dueTimeInput;
    Switch reminderSwitch;
    Button saveTaskBtn, aiSuggestBtn;

    FirebaseAuth auth;
    DatabaseReference database;

    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // 🔥 Firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        // 🧩 Bind UI
        taskNameInput = findViewById(R.id.task_name_input);
        taskDescInput = findViewById(R.id.task_desc_input);
        dueTimeInput = findViewById(R.id.due_time_input);
        reminderSwitch = findViewById(R.id.reminder_switch);
        saveTaskBtn = findViewById(R.id.save_task_btn);
        aiSuggestBtn = findViewById(R.id.aiSuggestBtn);

        // ⚡ IMPORTANT FIX (Dropdown)
        priorityDropdown = findViewById(R.id.priority_dropdown);

        String[] priorities = {"Low", "Medium", "High"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                priorities
        );
        priorityDropdown.setAdapter(adapter);

        // ⏰ TIME PICKER
        dueTimeInput.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        selectedTime = String.format(Locale.getDefault(),
                                "%02d:%02d", hourOfDay, minute);
                        dueTimeInput.setText(selectedTime);
                    },
                    12, 0, false
            );
            dialog.show();
        });

        // 🤖 AI BUTTON
        aiSuggestBtn.setOnClickListener(v -> generateAITask());

        // 💾 SAVE BUTTON
        saveTaskBtn.setOnClickListener(v -> saveTaskToFirebase());
    }

    // 🤖 AI LOGIC (RULE BASED)
    private void generateAITask() {

        String input = taskNameInput.getText().toString().toLowerCase();

        if (input.contains("fever")) {
            taskNameInput.setText("Take Paracetamol & Rest");
            taskDescInput.setText("Stay hydrated and monitor temperature");
        }
        else if (input.contains("headache")) {
            taskNameInput.setText("Rest in dark room");
            taskDescInput.setText("Avoid screen exposure");
        }
        else if (input.contains("cough")) {
            taskNameInput.setText("Drink warm fluids");
            taskDescInput.setText("Use honey & warm water");
        }
        else {
            taskNameInput.setText("Health monitoring task");
            taskDescInput.setText("General care suggestion");
        }

        Toast.makeText(this, "AI Task Generated ✨", Toast.LENGTH_SHORT).show();
    }

    // 💾 SAVE FIREBASE
    private void saveTaskToFirebase() {

        String name = taskNameInput.getText().toString().trim();
        String desc = taskDescInput.getText().toString().trim();
        String priority = priorityDropdown.getText().toString();
        boolean reminder = reminderSwitch.isChecked();

        if (name.isEmpty()) {
            Toast.makeText(this, "Task name required", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = (auth.getCurrentUser() != null)
                ? auth.getCurrentUser().getUid()
                : "guest";

        String taskId = database.child("users")
                .child(userId)
                .child("tasks")
                .push()
                .getKey();

        Map<String, Object> task = new HashMap<>();
        task.put("name", name);
        task.put("description", desc);
        task.put("priority", priority);
        task.put("time", selectedTime);
        task.put("reminder", reminder);
        task.put("aiGenerated", true);
        task.put("timestamp", System.currentTimeMillis());

        database.child("users")
                .child(userId)
                .child("tasks")
                .child(taskId)
                .setValue(task)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Task Saved ✅", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
