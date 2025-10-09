package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

import java.util.ArrayList;
import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {
    private List<QrScan> scans;

    public ScanAdapter(List<QrScan> scans) {
        this.scans = scans != null ? scans : new ArrayList<>();
    }

    public void updateScans(List<QrScan> newScans) {
        this.scans = newScans != null ? newScans : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QrScan scan = scans.get(position);  // Asume scans no null (por constructor/update)
        if (scan != null) {
            holder.contentText.setText("Contenido: " + scan.getContent());  // Usa getter estándar (no field directo)
            holder.timeText.setText("Hora: " + scan.getFormattedTime());  // Requiere agregar este método en QrScan (ver abajo)
        }
    }

    @Override
    public int getItemCount() {
        return scans != null ? scans.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentText, timeText;

        public ViewHolder(@NonNull View itemView) {  // ¡Fix: Agrega @NonNull para compliance
            super(itemView);
            contentText = itemView.findViewById(R.id.content_text);  // Resuelve con XML
            timeText = itemView.findViewById(R.id.time_text);  // Resuelve con XML
        }
    }
}
