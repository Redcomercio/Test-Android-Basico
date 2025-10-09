package com.orsanredcomercio.testandroidredcomercio.ui.history;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.orsanredcomercio.testandroidredcomercio.data.dao.QrScanDao;
import com.orsanredcomercio.testandroidredcomercio.data.database.AppDatabase;
import com.orsanredcomercio.testandroidredcomercio.data.entity.QrScan;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final LiveData<List<QrScan>> allScans;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        allScans = AppDatabase.getDatabase(application).qrScanDao().getAllScans();
    }

    public LiveData<List<QrScan>> getAllScans() {
        return allScans;
    }
}