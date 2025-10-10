/**
 * Maneja los datos del historial de escaneos QR de forma reactiva.
 * Provee una LiveData<List<QrScan>> que se actualiza automáticamente
 * cuando cambian los datos en la BD (ej. nuevo insert desde QrScanViewModel).
 */
package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.data.repository.QrScanRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final LiveData<List<QrScan>> allScans;
    private final QrScanRepository repository;  // Referencia para acceso a datos via Repository

    // Constructor: Inicializa Repository para abstraer BD
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new QrScanRepository(application);  // Usa Repository (no DAO directo)
        allScans = repository.getAllScans();  // Reactivo via Room
    }

    // Devuelve LiveData con todos los escaneos (ordenados por timestamp DESC)
    public LiveData<List<QrScan>> getAllScans() {
        return allScans;
    }
}