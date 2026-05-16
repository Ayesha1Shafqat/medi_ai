package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class personal_info_view extends AppCompatActivity {

    // ================= UI COMPONENTS =================

    private ImageButton btnBack;

    private MaterialButton btnEdit;

    private TextView txtName,
            txtAge,
            txtPhone,
            txtBlood;

    // ================= FIREBASE =================

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_view);

        // ================= INITIALIZE VIEWS =================

        btnBack = findViewById(R.id.btnBack);

        btnEdit = findViewById(R.id.btnEdit);

        txtName = findViewById(R.id.txtName);

        txtAge = findViewById(R.id.txtAge);

        txtPhone = findViewById(R.id.txtPhone);

        txtBlood = findViewById(R.id.txtBlood);

        // ================= FIREBASE =================

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            String userId = currentUser.getUid();

            userRef = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(userId);

            loadUserData();

        }

        // ================= BACK BUTTON =================

        btnBack.setOnClickListener(v -> finish());

        // ================= EDIT BUTTON =================

        btnEdit.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                           personal_info_view.this,
                            PersonalInfoActivity.class
                    );

            startActivity(intent);

        });

    }

    // ================= LOAD USER DATA =================

    private void loadUserData() {

        userRef.addValueEventListener(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            String name =
                                    snapshot.child("name")
                                            .getValue(String.class);

                            String age =
                                    snapshot.child("age")
                                            .getValue(String.class);

                            String phone =
                                    snapshot.child("phone")
                                            .getValue(String.class);

                            String blood =
                                    snapshot.child("blood")
                                            .getValue(String.class);

                            // ================= SET DATA =================

                            txtName.setText(name);

                            txtAge.setText(age);

                            txtPhone.setText(phone);

                            txtBlood.setText(blood);

                        }

                    }

                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError error) {

                        Toast.makeText(
                                personal_info_view.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

}
