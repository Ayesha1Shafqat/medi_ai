package com.example.medi_ai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import android.widget.LinearLayout;

public class EmergencyActivity extends AppCompatActivity {

    TextView tvFavName, tvFavNumber;
    Button btnAddFav, btn1122, btnPolice, btnFirstAid, btnNearby;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        initViews();
        initData();
        setClicks();
    }

    private void initViews() {
        tvFavName = findViewById(R.id.tvFavName);
        tvFavNumber = findViewById(R.id.tvFavNumber);

        btnAddFav = findViewById(R.id.btnAddFav);
        btn1122 = findViewById(R.id.btn1122);
        btnPolice = findViewById(R.id.btnPolice);
        btnFirstAid = findViewById(R.id.btnFirstAid);
        btnNearby = findViewById(R.id.btnNearby);
    }

    private void initData() {
        sp = getSharedPreferences("SOS_APP", MODE_PRIVATE);
        loadContact();
    }

    private void setClicks() {

        btnAddFav.setOnClickListener(v -> showAddContact());

        btn1122.setOnClickListener(v -> call("1122"));
        btnPolice.setOnClickListener(v -> call("15"));
        btnFirstAid.setOnClickListener(v -> call("1122"));

        btnNearby.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=hospital near me")))
        );

        tvFavNumber.setOnClickListener(v -> {
            String number = sp.getString("number", "");
            if (!number.isEmpty()) call(number);
        });
    }

    private void showAddContact() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Emergency Contact");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextInputEditText nameInput = new TextInputEditText(this);
        nameInput.setHint("Name");

        TextInputEditText numberInput = new TextInputEditText(this);
        numberInput.setHint("Number");
        numberInput.setInputType(android.text.InputType.TYPE_CLASS_PHONE);

        layout.addView(nameInput);
        layout.addView(numberInput);

        builder.setView(layout);

        builder.setPositiveButton("Save", (d, w) -> {

            sp.edit()
                    .putString("name", nameInput.getText().toString())
                    .putString("number", numberInput.getText().toString())
                    .apply();

            loadContact();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }

    private void loadContact() {

        String name = sp.getString("name", "");
        String number = sp.getString("number", "");

        if (!name.isEmpty()) {
            tvFavName.setText(name);
            tvFavNumber.setText(number);
        }
    }

    private void call(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
}
