package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentHistoryBinding;
import com.orsanredcomercio.testandroidredcomercio.ui.history.HistoryAdapter;  // Asume Adapter (ver nota si no existe)

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private HistoryAdapter adapter;  // Adapter para RecyclerView (asumido)

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup RecyclerView (solo si adapter existe)
        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter();  // Inicializa adapter
        recyclerView.setAdapter(adapter);

        // Título (opcional: Si ViewModel tiene texto, observa; aquí hardcodeado en XML)
        final TextView titleText = binding.textNotifications;
        titleText.setText("Historial de Escaneos");  // Opcional: Personaliza

        // Fix 4: Observer para allScans con empty state
        historyViewModel.getAllScans().observe(getViewLifecycleOwner(), scans -> {
            final TextView emptyText = binding.emptyText;
            List<String> contents = new ArrayList<>();  // O usa streams: scans.stream().map(QrScan::getContent).collect(Collectors.toList());
            if (scans != null) {
                for (QrScan scan : scans) {
                    contents.add(scan.getContent());  // Extrae content como String
                }
            }

            if (contents.isEmpty()) {  // O chequea scans == null || scans.isEmpty()
                emptyText.setText("No hay escaneos aún. ¡Empieza a escanear!");
                emptyText.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                adapter.submitList(contents);  // Cambio: Usa submitList (eficiente)
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}