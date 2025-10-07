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

import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentHistoryBinding;

import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Example data
        List<String> historyItems = Arrays.asList("Transaction 1", "Transaction 2", "Transaction 3");

        // Set up adapter
        HistoryAdapter adapter = new HistoryAdapter(historyItems);
        binding.historyRecyclerView.setAdapter(adapter);
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}