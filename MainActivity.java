package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView typingText;

    private final String text = "MediAI";
    private int index = 0;

    private final Handler handler = new Handler(Looper.getMainLooper());

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typingText = findViewById(R.id.typingText);

        auth = FirebaseAuth.getInstance();

        startTypingEffect();
    }

    private void startTypingEffect() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (index < text.length()) {
                    typingText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 150);
                } else {
                    checkUserAndNavigate();
                }
            }
        }, 500);
    }

    // 🔥 SAFE NAVIGATION (UPDATED)
    private void checkUserAndNavigate() {

        // ✅ safety check added (race-condition protection)
        if (isFinishing() || isDestroyed()) {
            return;
        }

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, FirstPageActivity.class));
        }

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
