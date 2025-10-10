/**
 * Repositorio de base de datos de escaneos de QR.
 * Envuelve el DAO para operaciones asíncronas.
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
            // Si falla (e.g., BD locked), Room lanza SQLiteException – atrápalo en ViewModel para Toast
            qrScanDao.insert(qrScan);
        });
    }
    // Opcional: Si necesitas callback para errores (no redundante, pero agrega si múltiples usos)
    // public interface OnInsertErrorListener { void onError(Exception e); }
    // Luego, en insert: catch (Exception e) { listener.onError(e); }
}
