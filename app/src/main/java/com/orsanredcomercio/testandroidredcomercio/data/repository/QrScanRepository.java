/**
 * Repositorio de base de datos de escaneos de QR.
 * Implementa la interfaz QrScanDao.
 */
package com.orsanredcomercio.testandroidredcomercio.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.orsanredcomercio.testandroidredcomercio.data.dao.QrScanDao;
import com.orsanredcomercio.testandroidredcomercio.data.database.AppDatabase;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QrScanRepository {
    private static final String TAG = "QrScanRepository";
    private final QrScanDao qrScanDao;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);  // Thread pool para async
    public QrScanRepository(Application application) {
        QrScanDao dao = AppDatabase.getDatabase(application).qrScanDao();
        this.qrScanDao = dao;
    }
    public LiveData<List<QrScan>> getAllScans() {
        return qrScanDao.getAllScans();
    }
    public LiveData<Integer> getTotalScans() {
        return qrScanDao.getTotalScans();
    }
    public void insert(QrScan qrScan) {
        executor.execute(() -> {
            try {
                qrScanDao.insert(qrScan);
            } catch (Exception e) {
                // Log o callback para error (ej. via interface)
                android.util.Log.e(TAG, "Error inserting QR", e);
            }
        });
    }
}
