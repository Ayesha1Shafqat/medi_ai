package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText otpInput;
    MaterialButton verifyBtn;
    TextView resendOtp;

    FirebaseAuth auth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpInput = findViewById(R.id.otpInput);
        verifyBtn = findViewById(R.id.verifyBtn);
        resendOtp = findViewById(R.id.resendOtp);

        auth = FirebaseAuth.getInstance();

        verificationId = getIntent().getStringExtra("verificationId");

        if (verificationId == null) {
            Toast.makeText(this, "Error: Verification failed", Toast.LENGTH_SHORT).show();
            finish();
        }

        verifyBtn.setOnClickListener(v -> verifyCode());

        resendOtp.setOnClickListener(v -> resendOtp());
    }

    // 🔥 VERIFY OTP
    private void verifyCode() {

        String code = otpInput.getText().toString().trim();

        if (TextUtils.isEmpty(code) || code.length() < 6) {
            Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(verificationId, code);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(this, "Verified ✔", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(this, create_password.class));
                        finish();

                    } else {
                        Toast.makeText(this, "Invalid OTP ❌", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 🔥 WHATSAPP-STYLE AUTO-FILL (IMPORTANT PART)
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                    String smsCode = credential.getSmsCode();

                    if (smsCode != null) {
                        otpInput.setText(smsCode);
                        verifyCode();
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(OtpActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String newVerificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {

                    verificationId = newVerificationId;
                }
            };

    // 🔁 RESEND OTP
    private void resendOtp() {

        String phone = getIntent().getStringExtra("phone");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
        );
    }
}
