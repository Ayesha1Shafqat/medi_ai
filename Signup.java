package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText nameInput, emailInput, phoneInput, passwordInput;
    MaterialButton signupBtn, googleBtn;

    FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ✅ MATCHED WITH XML IDS
        nameInput = findViewById(R.id.name);
        emailInput = findViewById(R.id.email);
        phoneInput = findViewById(R.id.phone);
        passwordInput = findViewById(R.id.password);

        signupBtn = findViewById(R.id.signupBtn);
        googleBtn = findViewById(R.id.googleBtn);

        // 🔥 FIREBASE INIT
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        signupBtn.setOnClickListener(v -> registerUser());

        googleBtn.setOnClickListener(v ->
                Toast.makeText(this, "Google Sign-In pending", Toast.LENGTH_SHORT).show()
        );
    }

    private void registerUser() {

        String name = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
        String email = emailInput.getText() != null ? emailInput.getText().toString().trim() : "";
        String phone = phoneInput.getText() != null ? phoneInput.getText().toString().trim() : "";
        String password = passwordInput.getText() != null ? passwordInput.getText().toString().trim() : "";

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        String uid = auth.getCurrentUser().getUid();

                        HashMap<String, Object> user = new HashMap<>();
                        user.put("uid", uid);
                        user.put("name", name);
                        user.put("email", email);
                        user.put("phone", phone);

                        database.child(uid).setValue(user)
                                .addOnSuccessListener(unused -> {

                                    Toast.makeText(this, "Signup Success ✔", Toast.LENGTH_SHORT).show();

                                    getSharedPreferences("MediAI", MODE_PRIVATE)
                                            .edit()
                                            .putString("name", name)
                                            .apply();

                                    startActivity(new Intent(this, HomeActivity.class));
                                    finish();

                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "DB Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );

                    } else {
                        Toast.makeText(this,
                                "Auth Error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
