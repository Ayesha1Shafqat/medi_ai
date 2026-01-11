package com.example.medi_ai;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class User_profile extends AppCompatActivity {

    private ImageView imgProfile;
    private TextView tvUserName, tvUserEmail;
    private TextView tvPostsCount, tvFollowersCount, tvFollowingCount;
    private ImageButton btnEditProfile;
    private MaterialButton btnLogout;
    private MaterialCardView profilePicCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // ================= INIT VIEWS =================
        imgProfile = findViewById(R.id.imgProfile);
        profilePicCard = findViewById(R.id.profilePicCard);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);

        tvPostsCount = findViewById(R.id.tvPostsCount);
        tvFollowersCount = findViewById(R.id.tvFollowersCount);
        tvFollowingCount = findViewById(R.id.tvFollowingCount);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // ================= SET DUMMY USER DATA =================
        tvUserName.setText("Ayesha Shafqat");
        tvUserEmail.setText("ayesha@example.com");
        tvPostsCount.setText("24");
        tvFollowersCount.setText("128");
        tvFollowingCount.setText("76");
        imgProfile.setImageResource(R.drawable.ic_profile);

        // ================= EDIT PROFILE =================
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Open EditProfileActivity if implemented
        });

        // ================= LOGOUT =================
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            // TODO: Perform logout (clear shared preferences / auth)
            finish();
        });
    }
}
