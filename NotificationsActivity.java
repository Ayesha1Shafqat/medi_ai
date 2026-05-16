package com.example.medi_ai;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyView;

    NotificationAdapter adapter;
    ArrayList<NotificationModel> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // init views
        recyclerView = findViewById(R.id.recyclerNotifications);
        emptyView = findViewById(R.id.emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new NotificationAdapter(list);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadNotifications();
    }

    private void loadNotifications() {

        db.collection("notifications")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    list.clear();

                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        String title = doc.getString("title");
                        String message = doc.getString("message");

                        list.add(new NotificationModel(
                                title != null ? title : "No Title",
                                message != null ? message : "No Message"
                        ));
                    }

                    adapter.notifyDataSetChanged();

                    // UI control
                    if (list.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    Log.d("FIRESTORE", "Loaded: " + list.size());

                })
                .addOnFailureListener(e -> {

                    Log.e("FIRESTORE_ERROR", "Failed", e);

                    Toast.makeText(this,
                            "Firebase error check logs",
                            Toast.LENGTH_SHORT).show();
                });
    }
}
