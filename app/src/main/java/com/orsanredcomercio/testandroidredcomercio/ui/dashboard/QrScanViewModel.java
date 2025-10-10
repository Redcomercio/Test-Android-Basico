/**
 * Clase viewModel para el escaneo de QR.
 * Sirve para manejar datos y estado de la UI de forma reactiva
 * y sobreviviente al ciclo de vida
 */
package com.orsanredcomercio.testandroidredcomercio.ui.dashboard;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.data.repository.QrScanRepository;

public class QrScanViewModel extends AndroidViewModel { // Renombrado para más claridad
    private final QrScanRepository repository;
    private final MutableLiveData<String> text = new MutableLiveData<>();

    // Seteo de texto inicial
    public QrScanViewModel(@NonNull Application application) {
        super(application);
        repository = new QrScanRepository(application);
        text.setValue("Escanea un QR para registrar la visita");
    }

    // Puente para la UI
    public LiveData<String> getText() {
        return text;
    }

    // inserta un nuevo escaneo en Room
    public void insert(QrScan qrScan) {
        repository.insert(qrScan);
        // Async via Repository; exceptions suben si needed (no hay manejo explícito aquí)
    }
}