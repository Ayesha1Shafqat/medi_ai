package com.example.medi_ai;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class BmiCalculatorActivity extends AppCompatActivity {

    TextInputEditText etHeight, etWeight;
    MaterialButton btnCalculateBMI;

    TextView tvBMIValue, tvCategory;

    CardView cardResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // Bind Views
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        btnCalculateBMI = findViewById(R.id.btnCalculateBMI);

        tvBMIValue = findViewById(R.id.tvBMIValue);
        tvCategory = findViewById(R.id.tvCategory);

        cardResult = findViewById(R.id.cardResult);

        // Button Click
        btnCalculateBMI.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {

        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        // Empty Validation
        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr)) {

            Toast.makeText(
                    this,
                    "Please enter height and weight",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        try {

            // Convert String → Float
            float heightCm = Float.parseFloat(heightStr);
            float weightKg = Float.parseFloat(weightStr);

            // Validate
            if (heightCm <= 0 || weightKg <= 0) {

                Toast.makeText(
                        this,
                        "Enter valid values",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // cm → meter
            float heightMeter = heightCm / 100;

            // BMI Formula
            float bmi = weightKg / (heightMeter * heightMeter);

            // Format BMI
            String bmiResult = String.format("%.2f", bmi);

            // Show Result Card
            cardResult.setVisibility(View.VISIBLE);

            // Set BMI
            tvBMIValue.setText("BMI: " + bmiResult);

            // Category Logic
            String category;

            if (bmi < 18.5) {

                category = "Underweight 😟";
                tvCategory.setTextColor(Color.parseColor("#2563EB"));

            }
            else if (bmi < 25) {

                category = "Normal 😊";
                tvCategory.setTextColor(Color.parseColor("#16A34A"));

            }
            else if (bmi < 30) {

                category = "Overweight 😐";
                tvCategory.setTextColor(Color.parseColor("#EA580C"));

            }
            else {

                category = "Obese ⚠️";
                tvCategory.setTextColor(Color.parseColor("#DC2626"));
            }

            // Show Category
            tvCategory.setText("Category: " + category);

        }
        catch (Exception e) {

            Toast.makeText(
                    this,
                    "Invalid Input!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}

