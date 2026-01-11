package com.example.medi_ai;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.media.RouteListingPreference;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottom_navigation extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);


        // Default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }}
//        bottomNavigation.setOnItemSelectedListener(item -> {
//
//            Fragment selectedFragment = null;
//
//            switch (item.getItemId()) {
//                case R.id.nav_home:
//                    selectedFragment = new HomeFragment();
//                    break;
//
//                case R.id.nav_bmi:
//                    selectedFragment = new BMIFragment();
//                    break;
//
//                case R.id.nav_healthlog:
//                    selectedFragment = new HealthLogFragment();
//                    break;
//
//                case R.id.nav_chat:
//                    selectedFragment = new chat_fragment(); // Corrected name
//                    break;
//
//                case R.id.nav_profile:
//                    selectedFragment = new UserProfileFragment();
//                    break;
//            }

//            if (selectedFragment != null) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, selectedFragment)
//                        .commit();
//            }
//
//            return true;
//        });
//    }
//
//
//}
