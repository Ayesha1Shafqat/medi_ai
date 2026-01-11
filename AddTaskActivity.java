package com.example.medi_ai;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText taskNameInput, taskDescInput, dueTimeInput;
    private Spinner prioritySpinner;
    private Switch reminderSwitch;
    private Button saveTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // ================== Toolbar ==================
        toolbar = findViewById(R.id.add_task_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back arrow
            getSupportActionBar().setTitle("Add Task");
        }

        // ================== Initialize Views ==================
        taskNameInput = findViewById(R.id.task_name_input);
        taskDescInput = findViewById(R.id.task_desc_input);
        dueTimeInput = findViewById(R.id.due_time_input);
        prioritySpinner = findViewById(R.id.priority_spinner);
        reminderSwitch = findViewById(R.id.reminder_switch);
        saveTaskBtn = findViewById(R.id.save_task_btn);

        // ================== Spinner Setup ==================
        String[] priorities = {"Low", "Medium", "High"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(spinnerAdapter);

        // ================== Time Picker ==================
        dueTimeInput.setOnClickListener(v -> showTimePicker());

        // ================== Save Button ==================
        saveTaskBtn.setOnClickListener(v -> saveTask());
    }

    // ================== Handle Toolbar Back Button ==================
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ================== Show Time Picker ==================
    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    String amPm = (selectedHour >= 12) ? "PM" : "AM";
                    int hour12 = selectedHour % 12;
                    if (hour12 == 0) hour12 = 12;
                    String formattedTime = String.format("%02d:%02d %s", hour12, selectedMinute, amPm);
                    dueTimeInput.setText(formattedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }

    // ================== Save Task Method ==================
    private void saveTask() {
        String taskName = taskNameInput.getText().toString().trim();
        String taskDesc = taskDescInput.getText().toString().trim();
        String dueTime = dueTimeInput.getText().toString().trim();
        String priority = prioritySpinner.getSelectedItem().toString();
        boolean reminderEnabled = reminderSwitch.isChecked();

        if (taskName.isEmpty()) {
            taskNameInput.setError("Task name is required");
            taskNameInput.requestFocus();
            return;
        }

        // TODO: Save task to database, shared preferences, or your preferred storage

        // For now, show a Toast
        Toast.makeText(this, "Task Saved:\n" +
                "Name: " + taskName + "\n" +
                "Desc: " + taskDesc + "\n" +
                "Time: " + dueTime + "\n" +
                "Priority: " + priority + "\n" +
                "Reminder: " + (reminderEnabled ? "Yes" : "No"), Toast.LENGTH_LONG).show();

        finish(); // Close activity after saving
    }
}
