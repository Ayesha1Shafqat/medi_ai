package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class bmi_calculator extends AppCompatActivity {

    private EditText etAge, etHeight, etWeight;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbOther;
    private MaterialButton btnCalculate;
    private TextView tvBMIValue, tvBMICategory, tvBottomHint;
    private ProgressBar bmiProgress;
    private ImageButton btnRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // ================= INITIALIZE VIEWS =================
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnRefresh = findViewById(R.id.btnRefresh);

        tvBMIValue = findViewById(R.id.tvBMIValue);
        tvBMICategory = findViewById(R.id.tvBMICategory);
        tvBottomHint = findViewById(R.id.tvBottomHint);
        bmiProgress = findViewById(R.id.bmiProgress);

        // ================= CALCULATE BUTTON =================
        btnCalculate.setOnClickListener(v -> calculateBMI());

        // ================= REFRESH BUTTON =================
        btnRefresh.setOnClickListener(v -> resetFields());
    }

    // ================= CALCULATE BMI =================
    private void calculateBMI() {
        String ageStr = etAge.getText().toString().trim();
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        if (TextUtils.isEmpty(ageStr)) {
            etAge.setError("Enter age");
            etAge.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(heightStr)) {
            etHeight.setError("Enter height");
            etHeight.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(weightStr)) {
            etWeight.setError("Enter weight");
            etWeight.requestFocus();
            return;
        }

        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select your gender", Toast.LENGTH_SHORT).show();
            return;
        }

        float heightCm = Float.parseFloat(heightStr);
        float weightKg = Float.parseFloat(weightStr);

        if (heightCm <= 0 || weightKg <= 0) {
            Toast.makeText(this, "Height and Weight must be positive", Toast.LENGTH_SHORT).show();
            return;
        }

        float heightM = heightCm / 100f;
        float bmi = weightKg / (heightM * heightM);

        // ================= DISPLAY RESULTS =================
        tvBMIValue.setText(String.format("BMI: %.1f", bmi));
        tvBMICategory.setText("Category: " + getBMICategory(bmi));

        // Animate ProgressBar
        bmiProgress.setMax(50); // Max BMI we consider
        bmiProgress.setProgress((int) bmi);
    }

    // ================= BMI CATEGORY =================
    private String getBMICategory(float bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 24.9) return "Normal weight";
        else if (bmi < 29.9) return "Overweight";
        else return "Obese";
    }

    // ================= RESET FIELDS =================
    private void resetFields() {
        etAge.setText("");
        etHeight.setText("");
        etWeight.setText("");
        rgGender.clearCheck();
        tvBMIValue.setText("BMI: ");
        tvBMICategory.setText("Category: ");
        bmiProgress.setProgress(0);
    }
}
