package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HealthInputActivity extends AppCompatActivity {

    // ================= UI =================

    private EditText etBP,
            etWater,
            etSymptoms;

    private Button btnSave;

    // ================= FIREBASE =================

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    private DatabaseReference healthRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // XML FILE
        setContentView(R.layout.activity_health_input);

        // ================= INIT VIEWS =================

        etBP = findViewById(R.id.etBP);

        etWater = findViewById(R.id.etWater);

        etSymptoms = findViewById(R.id.etSymptoms);

        btnSave = findViewById(R.id.btnSave);

        // ================= FIREBASE =================

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        // ================= DATABASE =================

        healthRef = FirebaseDatabase.getInstance()
                .getReference("DailyHealth");

        // ================= SAVE BUTTON =================

        btnSave.setOnClickListener(v -> saveHealthData());

    }

    // ================= SAVE DATA =================

    private void saveHealthData() {

        String bp =
                etBP.getText().toString().trim();

        String water =
                etWater.getText().toString().trim();

        String symptoms =
                etSymptoms.getText().toString().trim();

        // ================= VALIDATION =================

        if (TextUtils.isEmpty(bp)) {

            etBP.setError("Blood pressure required");

            return;
        }

        if (TextUtils.isEmpty(water)) {

            etWater.setError("Water intake required");

            return;
        }

        // ================= CHECK USER =================

        if (currentUser == null) {

            Toast.makeText(
                    this,
                    "User not logged in",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // ================= USER ID =================

        String userId = currentUser.getUid();

        // ================= DATE =================

        String currentDate =
                new SimpleDateFormat(
                        "dd-MM-yyyy HH:mm",
                        Locale.getDefault()
                ).format(new Date());

        // ================= RECORD ID =================

        String recordId =
                healthRef.push().getKey();

        // ================= MODEL OBJECT =================

        DailyHealthModel model =
                new DailyHealthModel(
                        bp,
                        water,
                        symptoms,
                        currentDate
                );

        // ================= SAVE TO FIREBASE =================

        if (recordId != null) {

            healthRef.child(userId)
                    .child(recordId)
                    .setValue(model)

                    .addOnSuccessListener(unused -> {

                        Toast.makeText(
                                this,
                                "Health Data Saved ✔",
                                Toast.LENGTH_SHORT
                        ).show();

                        clearFields();

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

    // ================= CLEAR FIELDS =================

    private void clearFields() {

        etBP.setText("");

        etWater.setText("");

        etSymptoms.setText("");

    }

}
