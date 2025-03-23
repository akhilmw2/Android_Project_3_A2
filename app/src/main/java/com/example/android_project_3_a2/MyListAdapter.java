package com.example.android_project_3_a2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }

    private final List<Item> items;
    private final OnItemClickListener listener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public MyListAdapter(List<Item> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(android.R.id.text1);
        }
        public void bind(final Item item, final OnItemClickListener listener, final int position, final int selectedPosition) {
            textView.setText(item.getName());
            // Change background if this position is selected
            if (position == selectedPosition) {
                itemView.setBackgroundColor(0xFFE0E0E0); // light gray highlight
            } else {
                itemView.setBackgroundColor(0x00000000); // transparent
            }
            itemView.setOnClickListener(v -> {
                listener.onItemClick(item, position);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Simple built-in layout for list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener, position, selectedPosition);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Call this from your fragment/activity when an item is clicked.
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
}
