/**
 * Clase para el escaneo de QR usando la camara del dispositivo
 * y ML Kit para detectar el contenido del QR.
 */
package com.orsanredcomercio.testandroidredcomercio.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.orsanredcomercio.testandroidredcomercio.R;
import com.orsanredcomercio.testandroidredcomercio.data.database.AppDatabase;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.databinding.FragmentQrScanBinding;
import com.orsanredcomercio.testandroidredcomercio.ui.dashboard.QrScanViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orsanredcomercio.testandroidredcomercio.ui.history.ScanAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QrScanFragment extends Fragment {
    private FragmentQrScanBinding binding;
    private PreviewView previewView;
    private Button scanButton;
    private static final int REQUEST_CAMERA_PERMISSION = 10;
    private ProcessCameraProvider cameraProvider;
    private boolean isScanning = false;  // Toggle para start/stop
    private RecyclerView scanRecyclerView;
    private ScanAdapter scanAdapter;

    // Método que configura UI, botón toggle y permisos
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQrScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ViewModel (crea QrScanViewModel si no existe; o remueve si no necesitas)
        QrScanViewModel qrScanViewModel = new ViewModelProvider(this).get(QrScanViewModel.class);
        final TextView textView = binding.textQr;  // Cambia a ID correcto en layout (no textDashboard)
        qrScanViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        previewView = binding.previewView;
        scanButton = binding.scanButton;

        scanButton.setOnClickListener(v -> {
            if (isScanning) {
                stopScan();
            } else {
                startScan();
            }
        });
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        // Setup RecyclerView para escaneos (opcional: Integra ScanAdapter) - AGREGADO AQUÍ (después de botón y permisos)
        scanRecyclerView = binding.scanRecyclerView;  // Asume ID agregado al XML (ej. debajo de scanButton)
        scanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scanAdapter = new ScanAdapter();  // Fix 5: Constructor vacío
        scanRecyclerView.setAdapter(scanAdapter);

        // Observe allScans del ViewModel (asumiendo QrScanViewModel tiene getAllScans() como en fixes previos)
        qrScanViewModel.getAllScans().observe(getViewLifecycleOwner(), scans -> {
            scanAdapter.submitList(scans);  // Fix 5: Actualiza eficientemente después de save
        });

        return root;  // Tipo de retorno corregido
    }
    // Verifica permisos antes de usar la camara
    private boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    // Método que inicia la camara
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider>
                cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindCameraUseCases();
                isScanning = true;
                scanButton.setText("Detener Escaneo");
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(requireContext(), "Error iniciando cámara", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }
    // Método que muestra el preview en pantalla y analiza frames en tiempo real para detectar QR
    @OptIn(markerClass = ExperimentalGetImage.class)
    private void bindCameraUseCases() {
        if (cameraProvider == null) return;

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()),
                imageProxy -> processImageProxy(imageProxy));

        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
    }
    // Convierte los frames de la camara en imágenes y analiza QR
    @SuppressLint("ExperimentalGetImage")
    @ExperimentalGetImage  // Fix para warning experimental (opt-in requerido)
    private void processImageProxy(ImageProxy imageProxy) {
        if (imageProxy == null || imageProxy.getImage() == null) {
            imageProxy.close();
            return;
        }

        InputImage image = InputImage.fromMediaImage(imageProxy.getImage(),
                imageProxy.getImageInfo().getRotationDegrees());

        BarcodeScanning.getClient().process(image)
                .addOnSuccessListener(barcodes -> {
                    if (!barcodes.isEmpty() && isScanning) {
                        Barcode barcode = barcodes.get(0);
                        String qrContent = barcode.getRawValue();
                        if (qrContent != null) {
                            saveScan(qrContent);
                            Toast.makeText(requireContext(),
                                    "QR escaneado correctamente: " + qrContent, Toast.LENGTH_LONG).show();
                            stopScan();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Fix 3: Agrega Toast para feedback solo si escaneando (evita spam en pausas)
                    if (isScanning) {
                        Toast.makeText(requireContext(), "Error procesando imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    // Maneja errores de ML Kit (e.g., QR inválido) - mantiene log para debug
                    e.printStackTrace();
                })
                .addOnCompleteListener(
                        release -> imageProxy.close());
    }
    // Guarda el contenido del QR en la BD
    private void saveScan(String content) {
        new Thread(() -> {
            try {
                AppDatabase.getDatabase
                        (requireContext().getApplicationContext()).qrScanDao().insert(new QrScan(content));
            } catch (Exception e) {
                // Fix 3: Agrega Toast en UI thread para error de guardado (ej. BD falla)
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Error guardando QR: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
                e.printStackTrace();
            }
        }).start();
    }
    // Controlan el flujo: start rebindea para reanudar análisis, stop libera cámara
    private void startScan() {
        if (cameraProvider != null && !isScanning) {
            bindCameraUseCases();
            isScanning = true;
            scanButton.setText("Detener Escaneo");
        } else if (cameraProvider == null) {
            startCamera();
        }
    }

    private void stopScan() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            isScanning = false;
            scanButton.setText("Iniciar Escaneo");
        }
    }
    // Método que responde al diálogo de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(requireContext(),
                        "Permiso de cámara requerido", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Al salir del fragment libera recursos
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopScan();
        binding = null;
    }
}
