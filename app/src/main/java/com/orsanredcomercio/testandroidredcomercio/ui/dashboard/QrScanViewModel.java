/**
 * Clase viewModel para el escaneo de QR.
 * Sirve para manejar datos y estado de la UI de forma reactiva
 * y sobreviviente al ciclo de vida
 */
package com.orsanredcomercio.testandroidredcomercio.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

import java.util.List;

public class QrScanViewModel extends ViewModel { // Renombrado para más claridad
    private final MutableLiveData<String> text = new MutableLiveData<>();
    private MutableLiveData<List<QrScan>> allScans = new MutableLiveData<>();
    // Seteo de texto inicial
    public QrScanViewModel() {
        text.setValue("Escanea un QR para registrar la visita");  // Texto actualizado
    }
    public LiveData<List<QrScan>> getAllScans() {
        return allScans;
    }
    // Observador de texto
    public LiveData<String> getText() {
        return text;
    }
}