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

        // Observe existente: Texto descriptivo (sin cambios)
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final TextView totalText = binding.totalText;
        homeViewModel.getTotalScans().observe(getViewLifecycleOwner(), count -> {
            if (count != null && count > 0) {
                // Estado con datos: Muestra el conteo (actualiza automáticamente al insertar QR)
                totalText.setText("Total escaneos: " + count);
            } else if (count != null) {
                // Estado vacío: BD tiene 0 escaneos
                totalText.setText("No hay escaneos aún. ¡Empieza a escanear!");
            } else {
                // Estado cargando/error: Mientras Room query se ejecuta
                totalText.setText("Cargando total de escaneos...");
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