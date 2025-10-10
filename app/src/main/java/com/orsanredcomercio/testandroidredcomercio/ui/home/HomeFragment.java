/**
 * Clase Fragment básica para la sección "Home" de la navegación.
 * Template estándar de Android Studio, integrado con MVVM y ViewBinding.
 * Se carga dinámicamente en MainActivity via Navigation Component.
 */
package com.orsanredcomercio.testandroidredcomercio.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    // Construye la vista y el ViewModel
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Total dinámico (reactivo desde Room via getTotalScans())
        final TextView totalText = binding.totalText;
        homeViewModel.getTotalScans().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                if (count > 0) {
                    totalText.setText(getString(R.string.total_scans, count));
                } else {
                    totalText.setText(getString(R.string.no_scans));
                }
            } else {
                // Estado inicial/cargando
                totalText.setText(getString(R.string.loading_scans));
            }
        });

        return root;
    }

    // Destruye el ViewModel y la vista
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}