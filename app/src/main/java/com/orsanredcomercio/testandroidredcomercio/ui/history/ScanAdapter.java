/**
 * Clase Adapter para RecyclerView.
 * Mejora HistoryAdapter maneja List<QrScan> (la entidad completa de la BD)
 * en lugar de strings simples, permitiendo mostrar detalles como contenido
 * del QR y hora formateada.
 * Fix 5: Migrado a ListAdapter con DiffUtil para actualizaciones eficientes (solo diffs se animan/actualizan).
 */
package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScanAdapter extends ListAdapter<QrScan, ScanAdapter.ViewHolder> {
    public ScanAdapter() {
        super(DIFF_CALLBACK);
    }

    // Uso: adapter.submitList(newScans); desde Fragment/ViewModel
    public void submitList(List<QrScan> newScans) {
        super.submitList(newScans != null ? newScans : new ArrayList<>());
    }

    // Crea un ViewHolder para cada elemento de la lista (sin cambios)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scan,
                parent, false);
        return new ViewHolder(view);
    }

    // Muestra el contenido de un elemento de la lista
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QrScan scan = getItem(position);
        holder.bind(scan);
    }

    // Retorna la cantidad de elementos de la lista (Usa getCurrentList())
    @Override
    public int getItemCount() {
        return getCurrentList().size();  // Automático y null-safe
    }

    // DiffUtil para comparar QrScan (calcula diffs eficientes por ID y contenido)
    private static final DiffUtil.ItemCallback<QrScan> DIFF_CALLBACK = new DiffUtil.ItemCallback<QrScan>() {
        @Override
        public boolean areItemsTheSame(@NonNull QrScan oldItem, @NonNull QrScan newItem) {
            // Items iguales si tienen el mismo ID (único en BD, auto-increment)
            return oldItem.getId() == newItem.getId();  // Asume QrScan tiene long getId()
        }

        @Override
        public boolean areContentsTheSame(@NonNull QrScan oldItem, @NonNull QrScan newItem) {
            // Contenidos iguales si campos clave coinciden (content y formattedTime)
            return Objects.equals(oldItem.getContent(), newItem.getContent()) &&  // Content del QR
                    Objects.equals(oldItem.getFormattedTime(), newItem.getFormattedTime());  // Hora formateada
        }
    };

    // Clase interna para almacenar los elementos de la lista
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView contentText, timeText;

        // Constructor ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentText = itemView.findViewById(R.id.content_text);  // Resuelve con XML
            timeText = itemView.findViewById(R.id.time_text);  // Resuelve con XML
        }

        // Eencapsula lógica de onBindViewHolder; chequea null
        public void bind(QrScan scan) {
            if (scan != null) {
                contentText.setText("Contenido: " + scan.getContent());
                timeText.setText("Hora: " + scan.getFormattedTime());
            }
        }
    }
}