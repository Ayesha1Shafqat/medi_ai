package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView typingText;
    private final String text = "MediAI";
    private int index = 0;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Edge-to-Edge (modern Android behavior)
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply system bar insets safely
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        typingText = findViewById(R.id.typingText);

        startTypingEffect();
    }

    private void startTypingEffect() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    typingText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 200); // typing speed
                } else {
                    openSignupScreen();
                }
            }
        }, 600); // initial delay before typing starts
    }

    private void openSignupScreen() {
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
            finish(); // prevent returning to splash
        }, 800); // pause after full text appears
    }
}
