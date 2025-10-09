package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentHistoryBinding;
import com.orsanredcomercio.testandroidredcomercio.ui.history.HistoryViewModel;
import com.orsanredcomercio.testandroidredcomercio.ui.history.ScanAdapter;

import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    private ScanAdapter adapter;
    private HistoryViewModel historyViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        // Observe LiveData<List<QrScan>> del ViewModel (sin formateo — Adapter maneja bind)
        historyViewModel.getAllScans().observe(getViewLifecycleOwner(), scans -> {
            if (adapter == null) {
                adapter = new ScanAdapter(scans);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateScans(scans);
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