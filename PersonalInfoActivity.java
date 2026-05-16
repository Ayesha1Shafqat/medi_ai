package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfoActivity extends AppCompatActivity {

    // UI

    ImageButton btnBack;

    EditText etName,
            etAge,
            etPhone,
            etBlood;

    MaterialButton btnSave;

    // FIREBASE

    DatabaseReference reference;

    FirebaseAuth mAuth;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // INIT VIEWS

        btnBack = findViewById(R.id.btn_back);

        etName = findViewById(R.id.et_name);

        etAge = findViewById(R.id.et_age);

        etPhone = findViewById(R.id.et_phone);

        etBlood = findViewById(R.id.et_blood);

        btnSave = findViewById(R.id.btn_save);

        // FIREBASE INIT

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        // DATABASE REFERENCE

        reference = FirebaseDatabase.getInstance()
                .getReference("Users");

        // BACK BUTTON

        btnBack.setOnClickListener(v -> finish());

        // SAVE BUTTON

        btnSave.setOnClickListener(v -> saveData());

    }

    // ================= SAVE DATA =================

    private void saveData() {

        String name =
                etName.getText().toString().trim();

        String age =
                etAge.getText().toString().trim();

        String phone =
                etPhone.getText().toString().trim();

        String blood =
                etBlood.getText().toString().trim();

        // VALIDATION

        if (TextUtils.isEmpty(name)) {

            etName.setError("Name required");

            return;
        }

        if (TextUtils.isEmpty(age)) {

            etAge.setError("Age required");

            return;
        }

        if (TextUtils.isEmpty(phone)) {

            etPhone.setError("Phone required");

            return;
        }

        if (TextUtils.isEmpty(blood)) {

            etBlood.setError("Blood group required");

            return;
        }

        // CHECK USER

        if (currentUser == null) {

            Toast.makeText(this,
                    "User not logged in",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        // USER ID

        String userId = currentUser.getUid();

        // CREATE MODEL OBJECT

        UserModel userModel =
                new UserModel(
                        name,
                        age,
                        phone,
                        blood
                );

        // SAVE TO FIREBASE

        reference.child(userId)
                .setValue(userModel)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            this,
                            "Data Saved Successfully ✔",
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();

                })

                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();

                });

    }

}
