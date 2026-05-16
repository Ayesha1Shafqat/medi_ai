package com.example.medi_ai;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final ArrayList<ChatMessage> chatMessages;

    // ───────── CONSTRUCTOR ─────────
    public ChatAdapter(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    // ───────── CREATE VIEW ─────────
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);

        return new ChatViewHolder(view);
    }

    // ───────── BIND DATA ─────────
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        ChatMessage msg = chatMessages.get(position);

        holder.txtMessage.setText(msg.getMessage());

        // Layout params
        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();

        if (msg.isUser()) {

            // ✅ USER MESSAGE
            params.gravity = Gravity.END;

            holder.txtMessage.setBackgroundResource(
                    R.drawable.bg_user_message
            );

            holder.txtMessage.setTextColor(Color.WHITE);

        } else {

            // ✅ BOT MESSAGE
            params.gravity = Gravity.START;

            holder.txtMessage.setBackgroundResource(
                    R.drawable.bg_bot_message
            );

            holder.txtMessage.setTextColor(Color.BLACK);
        }

        holder.txtMessage.setLayoutParams(params);
    }

    // ───────── ITEM COUNT ─────────
    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    // ───────── VIEW HOLDER ─────────
    static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView txtMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
    }
}
