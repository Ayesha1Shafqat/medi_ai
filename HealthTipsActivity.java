package com.example.medi_ai;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HealthTipsActivity extends AppCompatActivity {

    private ImageButton backBtn;

    private CardView cardWater, cardSleep, cardFood, cardExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edge-to-edge UI
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_tips);

        // Handle system padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupBackButton();
        setupCardClicks();
    }

    private void initViews() {
        backBtn = findViewById(R.id.backBtn);

        cardWater = findViewById(R.id.cardWater);
        cardSleep = findViewById(R.id.cardSleep);
        cardFood = findViewById(R.id.cardFood);
        cardExercise = findViewById(R.id.cardExercise);
    }

    private void setupBackButton() {
        backBtn.setOnClickListener(v -> finish());
    }

    private void setupCardClicks() {

        cardWater.setOnClickListener(v ->
                Toast.makeText(this, "Stay hydrated 💧", Toast.LENGTH_SHORT).show());

        cardSleep.setOnClickListener(v ->
                Toast.makeText(this, "Good sleep = good health 😴", Toast.LENGTH_SHORT).show());

        cardFood.setOnClickListener(v ->
                Toast.makeText(this, "Eat clean 🍎", Toast.LENGTH_SHORT).show());

        cardExercise.setOnClickListener(v ->
                Toast.makeText(this, "Keep moving 🏃", Toast.LENGTH_SHORT).show());
    }
}
