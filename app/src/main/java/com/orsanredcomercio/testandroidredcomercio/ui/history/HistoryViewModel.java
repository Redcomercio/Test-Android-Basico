/**
 * Maneja los datos del historial de escaneos QR de forma reactiva.
 * Provee una LiveData<List<QrScan>> que se actualiza automáticamente
 * cuando cambian los datos en la BD (ej. nuevo insert desde el Fragment de escaneo).
 */
package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.orsanredcomercio.testandroidredcomercio.data.dao.QrScanDao;
import com.orsanredcomercio.testandroidredcomercio.data.database.AppDatabase;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import com.orsanredcomercio.testandroidredcomercio.data.repository.QrScanRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final LiveData<List<QrScan>> allScans;
    private final QrScanRepository repository;  // Nueva referencia: Inyectada para acceso a datos
    // El constructor se llama al crear el ViewModel (en el Fragment)
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        // Inicializa el Repository con Application (para DB singleton seguro)
        repository = new QrScanRepository(application);
        // Usa el Repo en lugar de DAO directo: Abstrae la lógica de BD
        allScans = repository.getAllScans();
    }
    // Método que devuelve LiveData<List<QrScan>> con todos los registros de la BD
    public LiveData<List<QrScan>> getAllScans() {
        return allScans;
    }
}