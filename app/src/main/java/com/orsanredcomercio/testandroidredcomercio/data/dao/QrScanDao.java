/**
 * Data Access Object para la tabla qr_scans.
 * Maneja operaciones relacionadas con la entidad de escaneo de QR "QrScan"
 */

package com.orsanredcomercio.testandroidredcomercio.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

import java.util.List;

@Dao
public interface QrScanDao {

    @Insert
    void insert(QrScan scan); // Inserta una nueva fila en la tabla

    // Método para obtener todos los escaneos
    @Query("SELECT * FROM qr_scans ORDER BY timestamp DESC")
    LiveData<List<QrScan>> getAllScans(); // LiveData para actualizaciones reactivas

    // Método para contar total de escaneos
    @Query("SELECT COUNT(*) FROM qr_scans")
    LiveData<Integer> getTotalScans(); // LiveData para total reactivo
}
