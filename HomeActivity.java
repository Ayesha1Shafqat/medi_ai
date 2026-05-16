package com.example.medi_ai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private CardView cardMedicine;
    private CardView cardTodayTasks;
    private MaterialButton btnAskAI;
    private FloatingActionButton fabSOS;
    private BottomNavigationView bottomNavigation;
    private ImageButton notificationBtn;

    // Firebase
    private DatabaseReference taskRef;

    // Gesture
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Firebase init
        taskRef = FirebaseDatabase.getInstance().getReference("Tasks");

        // Init Views
        notificationBtn = findViewById(R.id.notificationBtn);
        btnAskAI = findViewById(R.id.btnAskAI);
        fabSOS = findViewById(R.id.fabSOS);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Views
        CardView cardSymptom = findViewById(R.id.cardSymptom);
        CardView cardBMI = findViewById(R.id.cardBMI);
        cardMedicine = findViewById(R.id.cardMedicine);
        CardView cardEmergency = findViewById(R.id.cardEmergency);
        cardTodayTasks = findViewById(R.id.cardTodayTasks);

        // ================= SWIPE GESTURE =================
        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    private static final int SWIPE_THRESHOLD = 100;
                    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {

                        float diffX = e2.getX() - e1.getX();
                        float diffY = e2.getY() - e1.getY();

                        if (Math.abs(diffX) > Math.abs(diffY)) {

                            if (Math.abs(diffX) > SWIPE_THRESHOLD &&
                                    Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {

                                if (diffX > 0) {
                                    // 👉 Right Swipe
                                    Toast.makeText(HomeActivity.this,
                                            "Swiped Right ➡️ Reports", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(HomeActivity.this, ReportsActivity.class));

                                } else {
                                    // 👈 Left Swipe
                                    Toast.makeText(HomeActivity.this,
                                            "Swiped Left ⬅️ AI Chat", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(HomeActivity.this, ChatbotActivity.class));
                                }
                                return true;
                            }
                        }
                        return false;
                    }
                });

        // ================= CLICK EVENTS =================

        cardTodayTasks.setOnClickListener(v ->
                startActivity(new Intent(this, TaskListActivity.class)));

        notificationBtn.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationsActivity.class)));

        btnAskAI.setOnClickListener(v ->
                startActivity(new Intent(this, ChatbotActivity.class)));

        cardSymptom.setOnClickListener(v ->
                startActivity(new Intent(this, SymptomCheckerActivity.class)));

        cardBMI.setOnClickListener(v ->
                startActivity(new Intent(this, BmiCalculatorActivity.class)));

        cardMedicine.setOnClickListener(v ->
                startActivity(new Intent(this, MedicineReminderActivity.class)));

        cardEmergency.setOnClickListener(v ->
                startActivity(new Intent(this, EmergencyActivity.class)));

        fabSOS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1122"));
            startActivity(intent);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) return true;

            else if (id == R.id.nav_ai)
                startActivity(new Intent(this, ChatbotActivity.class));

            else if (id == R.id.nav_reports)
                startActivity(new Intent(this, ReportsActivity.class));

            else if (id == R.id.nav_profile)
                startActivity(new Intent(this, UserProfileActivity.class));

            return true;
        });
    }


    // ================= TOUCH HANDLING =================
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}
