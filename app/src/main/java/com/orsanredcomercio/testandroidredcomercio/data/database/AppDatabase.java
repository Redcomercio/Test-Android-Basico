/**
 * Representa la base de datos, usando Room de Android Architecture Components.
 * Actúa como un "contenedor" para la BD SQLite.
 */
package com.orsanredcomercio.testandroidredcomercio.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.orsanredcomercio.testandroidredcomercio.data.dao.QrScanDao;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

@Database(entities = {QrScan.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Métodos de acceso al DAO
    public abstract QrScanDao qrScanDao();

    // Crea una instancia de la BD si no existe ya
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
