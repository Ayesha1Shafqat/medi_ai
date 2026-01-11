package com.example.medi_ai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<ChatMessage> chatMessages;

    public ChatAdapter(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) { // user
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatbot, parent, false);
        } else { // bot
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatbot, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage msg = chatMessages.get(position);
        holder.txtMessage.setText(msg.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).isUser() ? 0 : 1;
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.etMessage);
        }
    }
}
