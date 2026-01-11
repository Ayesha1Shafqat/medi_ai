package com.example.medi_ai;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class healthlog extends AppCompatActivity {

    private Toolbar toolbarHealthLog;
    private TextView tvSelectedDate, tvWeight, tvBMI, tvSleepValue, tvMedicationValue;
    private FloatingActionButton fabAddLog;

    private Calendar selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthlog);

        // ================= INIT VIEWS =================
        toolbarHealthLog = findViewById(R.id.toolbarHealthLog);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvWeight = findViewById(R.id.tvWeight);
        tvBMI = findViewById(R.id.tvBMI);
        tvSleepValue = findViewById(R.id.tvSleepValue);
        tvMedicationValue = findViewById(R.id.tvMedicationValue);
        fabAddLog = findViewById(R.id.fabAddLog);

        // ================= TOOLBAR BACK BUTTON =================
        toolbarHealthLog.setNavigationOnClickListener(v -> finish());

        // ================= DATE SELECTION =================
        selectedDate = Calendar.getInstance();
        updateDateText();

        findViewById(R.id.btnSelectDate).setOnClickListener(v -> showDatePicker());

        // ================= FAB CLICK =================
        fabAddLog.setOnClickListener(v -> {
            // TODO: Open add health log activity/dialog
            Toast.makeText(this, "Add Health Log clicked", Toast.LENGTH_SHORT).show();
        });

        // ================= LOAD HEALTH DATA =================
        loadHealthDataForDate(selectedDate);
    }

    // ================= SHOW DATE PICKER =================
    private void showDatePicker() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate.set(year1, month1, dayOfMonth);
                    updateDateText();
                    loadHealthDataForDate(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    // ================= UPDATE DATE TEXT =================
    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault());
        tvSelectedDate.setText(sdf.format(selectedDate.getTime()));
    }

    // ================= LOAD HEALTH DATA =================
    private void loadHealthDataForDate(Calendar date) {
        // TODO: Fetch health data from database/shared preferences
        // For now, using dummy data
        tvWeight.setText("Weight: 68 kg");
        tvBMI.setText("BMI: 23.5");
        tvSleepValue.setText("7h, Quality: Good");
        tvMedicationValue.setText("Taken");

        // You can later implement dynamic loading from SQLite or Room
    }
}
