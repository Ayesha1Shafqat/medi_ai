package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEt, passwordEt;
    private MaterialButton loginBtn, googleBtn;
    private TextView signUpLink, forgotPassword;
    private ProgressBar progressBar;
    private ImageButton backBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Views
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        googleBtn = findViewById(R.id.googleBtn);
        signUpLink = findViewById(R.id.signUpLink);
        forgotPassword = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.backBtn);

        // Back
        backBtn.setOnClickListener(v -> finish());

        // Login
        loginBtn.setOnClickListener(v -> loginUser());

        // Google (placeholder)
        googleBtn.setOnClickListener(v ->
                Toast.makeText(this, "Google Sign-In coming soon", Toast.LENGTH_SHORT).show()
        );

        // Signup
        signUpLink.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );

        // Forgot password
        forgotPassword.setOnClickListener(v -> resetPassword());
    }

    // LOGIN
    private void loginUser() {

        String email = emailEt.getText() != null ? emailEt.getText().toString().trim() : "";
        String password = passwordEt.getText() != null ? passwordEt.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Email required");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("Invalid email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Password required");
            return;
        }

        if (password.length() < 6) {
            passwordEt.setError("Min 6 characters");
            return;
        }

        setLoading(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    setLoading(false);

                    if (task.isSuccessful()) {

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(this,
                                task.getException() != null ?
                                        task.getException().getMessage()
                                        : "Login failed",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // RESET PASSWORD
    private void resetPassword() {

        String email = emailEt.getText() != null ? emailEt.getText().toString().trim() : "";

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Enter email first");
            return;
        }

        setLoading(true);

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {

                    setLoading(false);

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this,
                                "Failed to send reset email",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // LOADING
    private void setLoading(boolean loading) {

        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);

        loginBtn.setEnabled(!loading);
        googleBtn.setEnabled(!loading);
        forgotPassword.setEnabled(!loading);
        signUpLink.setEnabled(!loading);
    }
}
