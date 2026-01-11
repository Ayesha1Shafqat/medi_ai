package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BMIFragment extends Fragment {

    private EditText etAge, etHeight, etWeight;
    private RadioGroup rgGender;
    private Button btnCalculate;
    private TextView tvBMIValue, tvBMICategory;
    private ProgressBar bmiProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bmifragment, container, false);

        // ===== Initialize Views =====
        etAge = view.findViewById(R.id.etAge);
        etHeight = view.findViewById(R.id.etHeight);
        etWeight = view.findViewById(R.id.etWeight);
        rgGender = view.findViewById(R.id.rgGender);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        tvBMIValue = view.findViewById(R.id.tvBMIValue);
        tvBMICategory = view.findViewById(R.id.tvBMICategory);
        bmiProgress = view.findViewById(R.id.bmiProgress);

        // ===== Button Click =====
        btnCalculate.setOnClickListener(v -> calculateBMI());

        return view;
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();

        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(ageStr)) {
            tvBMIValue.setText("Please fill all fields");
            tvBMICategory.setText("");
            return;
        }

        try {
            float heightCm = Float.parseFloat(heightStr);
            float weightKg = Float.parseFloat(weightStr);

            float heightM = heightCm / 100f;
            float bmi = weightKg / (heightM * heightM);

            tvBMIValue.setText(String.format("BMI: %.1f", bmi));
            bmiProgress.setProgress((int) bmi);

            // Category
            String category;
            if (bmi < 18.5) category = "Underweight";
            else if (bmi < 25) category = "Normal";
            else if (bmi < 30) category = "Overweight";
            else category = "Obese";

            tvBMICategory.setText("Category: " + category);

        } catch (NumberFormatException e) {
            tvBMIValue.setText("Invalid input");
            tvBMICategory.setText("");
        }
    }
}
