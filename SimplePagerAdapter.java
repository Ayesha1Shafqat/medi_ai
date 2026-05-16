package com.example.medi_ai;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SimplePagerAdapter extends RecyclerView.Adapter<SimplePagerAdapter.ViewHolder> {

    String[] pages = {"Home Page", "AI Chat Page", "Reports Page"};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TextView tv = new TextView(parent.getContext());

        // ✅ MUST for ViewPager2 stability
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(28);

        // 🔥 SAFE padding (not extreme)
        tv.setPadding(20, 20, 20, 20);

        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(pages[position]);
    }

    @Override
    public int getItemCount() {
        return pages.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
