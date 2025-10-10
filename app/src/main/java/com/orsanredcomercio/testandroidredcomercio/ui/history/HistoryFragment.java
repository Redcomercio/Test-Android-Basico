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
import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentHistoryBinding;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private ScanAdapter adapter;  // Cambio: ScanAdapter en lugar de HistoryAdapter

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScanAdapter();  // Cambio: ScanAdapter (maneja QrScan directo)
        recyclerView.setAdapter(adapter);

        historyViewModel.getAllScans().observe(getViewLifecycleOwner(), scans -> {
            final TextView emptyText = binding.emptyText;
            // + Corrección: Chequeo explícito para null (Room puede emitir null inicial)
            if (scans == null || scans.isEmpty()) {
                emptyText.setText(getString(R.string.empty_history));
                emptyText.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                adapter.submitList(scans);
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