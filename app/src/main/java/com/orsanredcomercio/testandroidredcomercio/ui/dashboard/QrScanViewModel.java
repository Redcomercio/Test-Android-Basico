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

public class QrScanViewModel extends AndroidViewModel {
    private final QrScanRepository repository;
    private final MutableLiveData<String> text = new MutableLiveData<>();

    public QrScanViewModel(@NonNull Application application) {
        super(application);
        repository = new QrScanRepository(application);
    }

    public void insert(QrScan qrScan) {
        repository.insert(qrScan);
    }
}