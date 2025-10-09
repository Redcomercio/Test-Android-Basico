/**
 * Maneja la lista de escaneos de QR.
 */
package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;  // Cambio: Extiende ListAdapter en lugar de RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView;

import com.orsanredcomercio.testandroidredcomercio.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;  // Para equals en DiffUtil (si no usas Java 8+)

public class HistoryAdapter extends ListAdapter<String, HistoryAdapter.ViewHolder> {  // Cambio: ListAdapter<String, ViewHolder>

    // Cambio: Constructor sin parámetros (ListAdapter maneja lista internamente)
    public HistoryAdapter() {
        super(DIFF_CALLBACK);  // Pasa el DiffUtil callback
    }

    // Crea un ViewHolder para cada elemento de la lista (sin cambios)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    // Muestra el contenido de un elemento de la lista (sin cambios, usa getItem(position))
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = getItem(position);  // Cambio: Usa getItem() de ListAdapter (más eficiente)
        holder.itemText.setText(item);
    }

    // Retorna la cantidad de elementos de la lista (override requerido, usa current list)
    @Override
    public int getItemCount() {
        return getCurrentList().size();  // Cambio: Usa getCurrentList() de ListAdapter
    }

    // Nuevo método: submitList para actualizar lista (reemplaza updateScans)
    // Llama desde Fragment: adapter.submitList(newItems);
    public void submitList(List<String> newItems) {
        super.submitList(newItems != null ? newItems : new ArrayList<>());  // Maneja null, como original
    }

    // Cambio: Removido updateScans (ya no necesario; usa submitList arriba)

    // DiffUtil para comparar items (nuevo: Calcula diffs eficientes)
    private static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            // Items iguales si son el mismo objeto o ID (para String, usa equals)
            return oldItem.equals(newItem);  // Asume contents únicos; si tienes ID, usa oldItem.getId() == newItem.getId()
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            // Contenidos iguales si strings son idénticos
            return Objects.equals(oldItem, newItem);  // Usa Objects.equals para null-safe
        }
    };

    // Clase interna para almacenar los elementos de la lista (sin cambios)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        // Constructor ViewHolder (sin cambios)
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
        }
    }
}