package com.example.medi_ai;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SymptomCheckerActivity extends AppCompatActivity {

    // ================= UI =================

    ImageButton backBtn;

    TextInputEditText etSymptoms;

    MaterialButton btnCheck;

    TextView txtResult, txtAdvice;

    CardView resultCard;

    Chip chipFever, chipHeadache, chipCough;

    // ================= FIREBASE =================

    FirebaseAuth mAuth;

    FirebaseUser currentUser;

    DatabaseReference symptomRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_symptom_checker);

        // ================= FIREBASE INIT =================

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        // ================= BIND VIEWS =================

        backBtn = findViewById(R.id.backBtn);

        etSymptoms = findViewById(R.id.etSymptoms);

        btnCheck = findViewById(R.id.btnCheck);

        txtResult = findViewById(R.id.txtResult);

        txtAdvice = findViewById(R.id.txtAdvice);

        resultCard = findViewById(R.id.resultCard);

        chipFever = findViewById(R.id.chipFever);

        chipHeadache = findViewById(R.id.chipHeadache);

        chipCough = findViewById(R.id.chipCough);

        // ================= BACK =================

        backBtn.setOnClickListener(v -> finish());

        // ================= CHIP AUTO INPUT =================

        chipFever.setOnClickListener(v ->
                etSymptoms.setText("fever"));

        chipHeadache.setOnClickListener(v ->
                etSymptoms.setText("headache"));

        chipCough.setOnClickListener(v ->
                etSymptoms.setText("cough"));

        // ================= ANALYZE =================

        btnCheck.setOnClickListener(v ->
                analyzeSymptoms());
    }

    // ================= AI ANALYSIS =================

    private void analyzeSymptoms() {

        String input =
                etSymptoms.getText()
                        .toString()
                        .toLowerCase()
                        .trim();

        // ================= VALIDATION =================

        if (input.isEmpty()) {

            Toast.makeText(
                    this,
                    "Describe your symptoms first",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // ================= SHOW AI LOADING =================

        resultCard.setVisibility(View.VISIBLE);

        txtResult.setText("🤖 AI analyzing...");

        txtAdvice.setText(
                "Please wait while MediAI processes your symptoms..."
        );

        // ================= AI DELAY EFFECT =================

        new Handler().postDelayed(() -> {

            String result;

            String advice;

            // ================= AI LOGIC =================

            if (input.contains("fever")
                    && input.contains("cough")) {

                result = "Flu / Viral Infection";

                advice =
                        "Rest, drink fluids, monitor temperature, and consult doctor if symptoms worsen.";

            }

            else if (input.contains("headache")) {

                result = "Tension Headache";

                advice =
                        "Reduce stress, sleep properly, and stay hydrated.";

            }

            else if (input.contains("chest pain")) {

                result = "⚠ High Risk Warning";

                advice =
                        "Seek immediate medical help. Chest pain can be serious.";

            }

            else if (input.contains("cold")) {

                result = "Common Cold";

                advice =
                        "Warm fluids, proper rest, and avoid cold exposure.";

            }

            else if (input.contains("vomit")
                    || input.contains("nausea")) {

                result = "Digestive Problem";

                advice =
                        "Avoid oily food and stay hydrated.";

            }

            else if (input.contains("weakness")) {

                result = "Fatigue / Weakness";

                advice =
                        "Take rest and improve nutrition intake.";

            }

            else {

                result = "Unclear Symptoms";

                advice =
                        "MediAI needs more details. Add more symptoms for better AI analysis.";
            }

            // ================= SHOW RESULT =================

            txtResult.setText(result);

            txtAdvice.setText(advice);

            // ================= SAVE TO FIREBASE =================

            saveToFirebase(input, result, advice);

        }, 1800);
    }

    // ================= SAVE DATA =================

    private void saveToFirebase(String symptoms,
                                String result,
                                String advice) {

        // ================= USER CHECK =================

        if (currentUser == null) {

            Toast.makeText(
                    this,
                    "User not logged in",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String userId = currentUser.getUid();

        // ================= DATABASE PATH =================

        symptomRef = FirebaseDatabase.getInstance()
                .getReference("SymptomHistory")
                .child(userId);

        String historyId =
                symptomRef.push().getKey();

        // ================= DATE =================

        String currentDate =
                new SimpleDateFormat(
                        "dd MMM yyyy hh:mm a",
                        Locale.getDefault()
                ).format(new Date());

        // ================= DATA MAP =================

        HashMap<String, Object> map =
                new HashMap<>();

        map.put("symptoms", symptoms);

        map.put("result", result);

        map.put("advice", advice);

        map.put("date", currentDate);

        // ================= SAVE =================

        symptomRef.child(historyId)
                .setValue(map)
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            SymptomCheckerActivity.this,
                            "AI analysis saved",
                            Toast.LENGTH_SHORT
                    ).show();

                })

                .addOnFailureListener(e -> {

                    Toast.makeText(
                            SymptomCheckerActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                });
    }
}
