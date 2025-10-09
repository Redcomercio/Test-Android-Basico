package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orsanredcomercio.testandroidredcomercio.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<String> items;

    public HistoryAdapter(List<String> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemText.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateScans(List<String> newItems) {
        this.items = newItems != null ? newItems : new ArrayList<>();  // Actualiza lista; maneja null
        notifyDataSetChanged();  // Refresca RecyclerView (simple y eficiente para listas pequeñas)
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
        }
    }
}