package com.example.medi_ai;

import static com.example.medi_ai.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private CardView tasksCard;
    private ImageButton notificationIcon, menuIcon;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Handle system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_activity_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Toolbar icons
        notificationIcon = findViewById(R.id.notification_icon);
        menuIcon = findViewById(R.id.menu_icon);

        notificationIcon.setOnClickListener(v ->
                Toast.makeText(HomeActivity.this, "Notifications Clicked", Toast.LENGTH_SHORT).show()
        );

        menuIcon.setOnClickListener(v ->
                Toast.makeText(HomeActivity.this, "Menu Clicked", Toast.LENGTH_SHORT).show()
        );

        // Today’s Tasks card click -> open AddTaskActivity
        tasksCard = findViewById(R.id.tasks_card);
        tasksCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });




        // BottomNavigationView setup
        bottomNavigation = findViewById(R.id.bottom_navigation);
//        bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    Toast.makeText(this, "Home Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_tasks:
//                    Toast.makeText(this, "Tasks Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_chatbot:
//                    Toast.makeText(this, "Chatbot Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_profile:
//                    Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//                    return false;
//            }
//        }

//);
    }
}
