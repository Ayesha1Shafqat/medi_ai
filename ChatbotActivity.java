package com.example.medi_ai;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatbotActivity extends AppCompatActivity {

    // 🔹 Views
    private ImageButton btnSend;
    private ImageButton btnVoice;

    private EditText etMessage;

    private RecyclerView chatRecyclerView;

    // 🔹 Chat Components
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        initViews();

        setupRecyclerView();

        setupClickListeners();

        // 🤖 Welcome Message
        addMessage(
                "Hi 👋 I am MediAI Assistant.\nHow can I help you today?",
                false
        );
    }

    // 🔹 Initialize Views
    private void initViews() {

        chatRecyclerView =
                findViewById(R.id.chat_recycler);

        btnSend =
                findViewById(R.id.send_btn);

        btnVoice =
                findViewById(R.id.voice_btn);

        etMessage =
                findViewById(R.id.message_input);
    }

    // 🔹 RecyclerView Setup
    private void setupRecyclerView() {

        chatMessages = new ArrayList<>();

        chatAdapter =
                new ChatAdapter(chatMessages);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);

        chatRecyclerView.setLayoutManager(layoutManager);

        chatRecyclerView.setAdapter(chatAdapter);
    }

    // 🔹 Button Clicks
    private void setupClickListeners() {

        // 📩 SEND BUTTON
        btnSend.setOnClickListener(v -> {

            String userMessage =
                    etMessage.getText()
                            .toString()
                            .trim();

            // ❌ Empty Check
            if (TextUtils.isEmpty(userMessage)) {

                Toast.makeText(
                        this,
                        "Please type a message",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // ✅ Show User Message
            addMessage(userMessage, true);

            // 🧹 Clear Input
            etMessage.setText("");

            // 🤖 Send To Gemini AI
            sendToAI(userMessage);
        });

        // 🎤 Voice Button
        btnVoice.setOnClickListener(v ->

                Toast.makeText(
                        this,
                        "Voice feature coming soon 🎤",
                        Toast.LENGTH_SHORT
                ).show()
        );
    }

    // 🤖 Send Message To Gemini AI
    private void sendToAI(String message) {

        GeminiService.askAI(
                message,

                new GeminiService.GeminiCallback() {

                    @Override
                    public void onSuccess(String response) {

                        runOnUiThread(() ->

                                addMessage(response, false)
                        );
                    }

                    @Override
                    public void onError(String error) {

                        runOnUiThread(() ->

                                addMessage(
                                        "Error:\n" + error,
                                        false
                                )
                        );
                    }
                }
        );
    }


    // 💬 Add Message To RecyclerView
    private void addMessage(
            String message,
            boolean isUser
    ) {

        chatMessages.add(
                new ChatMessage(message, isUser)
        );

        chatAdapter.notifyItemInserted(
                chatMessages.size() - 1
        );

        chatRecyclerView.scrollToPosition(
                chatMessages.size() - 1
        );
    }
}
