package com.example.medi_ai;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    ImageButton backBtn;

    LinearLayout micLayout, storageLayout,
            privacyLayout, aboutLayout,
            languageLayout;

    Switch notificationSwitch, darkModeSwitch;

    MaterialButton logoutBtn;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_settings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom);

            return insets;
        });

        auth = FirebaseAuth.getInstance();

        initViews();

        clickListeners();
    }

    private void initViews() {

        backBtn = findViewById(R.id.backBtn);

        micLayout = findViewById(R.id.micLayout);
        storageLayout = findViewById(R.id.storageLayout);
        privacyLayout = findViewById(R.id.privacyLayout);
        aboutLayout = findViewById(R.id.aboutLayout);
        languageLayout = findViewById(R.id.languageLayout);

        notificationSwitch = findViewById(R.id.notificationSwitch);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        logoutBtn = findViewById(R.id.logoutBtn);
    }

    private void clickListeners() {

        // BACK BUTTON

        backBtn.setOnClickListener(v -> finish());

        // NOTIFICATION SWITCH

        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                Toast.makeText(this,
                        "Notifications Enabled",
                        Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this,
                        "Notifications Disabled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // DARK MODE

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );

                Toast.makeText(this,
                        "Dark Mode Enabled",
                        Toast.LENGTH_SHORT).show();

            } else {

                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );

                Toast.makeText(this,
                        "Dark Mode Disabled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // MICROPHONE SETTINGS

        micLayout.setOnClickListener(v -> {

            try {

                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                );

                intent.setData(Uri.parse(
                        "package:" + getPackageName()
                ));

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(this,
                        "Unable to open microphone settings",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // STORAGE SETTINGS

        storageLayout.setOnClickListener(v -> {

            try {

                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                );

                intent.setData(Uri.parse(
                        "package:" + getPackageName()
                ));

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(this,
                        "Unable to open storage settings",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // LANGUAGE

        languageLayout.setOnClickListener(v -> {

            Toast.makeText(this,
                    "Language Feature Coming Soon",
                    Toast.LENGTH_SHORT).show();
        });

        // PRIVACY POLICY

        privacyLayout.setOnClickListener(v -> {

            try {

                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com")
                );

                startActivity(browserIntent);

            } catch (Exception e) {

                Toast.makeText(this,
                        "Unable to open privacy policy",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // ABOUT APP

        aboutLayout.setOnClickListener(v -> {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

            builder.setTitle("Medi AI");

            builder.setMessage(
                    "Version 1.0\n\n" +
                            "AI Powered Healthcare Assistant App.\n\n" +
                            "Developed using Android Studio & Firebase."
            );

            builder.setPositiveButton("OK",
                    (dialog, which) -> dialog.dismiss());

            builder.show();
        });

        // LOGOUT

        logoutBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

            builder.setTitle("Logout");

            builder.setMessage(
                    "Are you sure you want to logout?"
            );

            builder.setPositiveButton("Yes",
                    (dialog, which) -> {

                        auth.signOut();

                        Intent intent =
                                new Intent(SettingsActivity.this,
                                        LoginActivity.class);

                        intent.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                        );

                        startActivity(intent);

                        finish();
                    });

            builder.setNegativeButton("Cancel",
                    (dialog, which) -> dialog.dismiss());

            builder.show();
        });
    }
}
