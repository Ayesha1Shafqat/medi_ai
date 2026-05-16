package com.example.medi_ai;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    TextView tvEnergy, tvHeart, tvBMI, tvBMIStatus;
    LineChart healthChart;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // UI Bind
        tvEnergy = findViewById(R.id.tvEnergy);
        tvHeart = findViewById(R.id.tvHeart);
        tvBMI = findViewById(R.id.tvBMI);
        tvBMIStatus = findViewById(R.id.tvBMIStatus);
        healthChart = findViewById(R.id.healthChart);

        // Firebase init
        db = FirebaseFirestore.getInstance();

        loadHealthData();
    }

    private void loadHealthData() {

        db.collection("HealthReports")
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (!queryDocumentSnapshots.isEmpty()) {

                        for (DocumentSnapshot data : queryDocumentSnapshots.getDocuments()) {

                            // ================= HEART RATE =================
                            String heartRate = data.getString("heartRate");

                            if (heartRate != null) {
                                tvHeart.setText(heartRate + " BPM");
                            }

                            // ================= ENERGY =================
                            String sleep = data.getString("sleep");
                            String water = data.getString("water");

                            try {
                                float s = sleep != null ? Float.parseFloat(sleep) : 0;
                                float w = water != null ? Float.parseFloat(water) : 0;

                                if (s >= 7 && w >= 2) {
                                    tvEnergy.setText("Excellent 😄");
                                } else {
                                    tvEnergy.setText("Low ⚠");
                                }

                            } catch (Exception e) {
                                tvEnergy.setText("Unknown");
                            }

                            // ================= BMI =================
                            float bmi = 22.4f;

                            tvBMI.setText(String.valueOf(bmi));

                            if (bmi < 18.5) {
                                tvBMIStatus.setText("Underweight");
                            } else if (bmi < 25) {
                                tvBMIStatus.setText("Normal");
                            } else {
                                tvBMIStatus.setText("Overweight");
                            }

                            // ================= CHART =================
                            loadChart(heartRate);
                        }

                    } else {
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                        loadChart(null); // fallback chart
                    }

                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // ================= CHART =================

    private void loadChart(String heartRate) {

        ArrayList<Entry> entries = new ArrayList<>();

        if (heartRate != null) {
            try {
                float hr = Float.parseFloat(heartRate);

                // simple real-like trend around value
                entries.add(new Entry(1, hr - 5));
                entries.add(new Entry(2, hr - 2));
                entries.add(new Entry(3, hr));
                entries.add(new Entry(4, hr + 3));
                entries.add(new Entry(5, hr));

            } catch (Exception e) {
                loadDefaultChart();
                return;
            }
        } else {
            loadDefaultChart();
            return;
        }

        drawChart(entries);
    }

    // fallback demo chart
    private void loadDefaultChart() {

        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(1, 65));
        entries.add(new Entry(2, 70));
        entries.add(new Entry(3, 72));
        entries.add(new Entry(4, 75));
        entries.add(new Entry(5, 73));

        drawChart(entries);
    }

    private void drawChart(ArrayList<Entry> entries) {

        LineDataSet dataSet = new LineDataSet(entries, "Heart Rate Trend");

        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setValueTextSize(10f);

        LineData lineData = new LineData(dataSet);

        healthChart.setData(lineData);
        healthChart.animateX(1000);
        healthChart.invalidate();
    }
}
