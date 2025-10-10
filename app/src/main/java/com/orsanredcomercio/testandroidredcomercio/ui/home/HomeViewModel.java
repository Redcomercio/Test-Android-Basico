/**
 * Es una versión extendida de AndroidViewModel
 * que maneja dos tipos de datos reactivos: Un texto estático
 * (mensaje descriptivo) y un conteo dinámico de escaneos totales
 * desde la BD (LiveData<Integer> del DAO).
 */
package com.orsanredcomercio.testandroidredcomercio.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.orsanredcomercio.testandroidredcomercio.data.repository.QrScanRepository;

public class HomeViewModel extends AndroidViewModel { // Cambio a AndroidViewModel para acceso a Context
    private final QrScanRepository repository;
    private LiveData<Integer> totalScans;
    private final MutableLiveData<String> text = new MutableLiveData<>();
    // El constructor se llama al crear el ViewModel (en el Fragment)
    public HomeViewModel(Application application) {
        super(application);
        repository = new QrScanRepository(application);
        totalScans = repository.getTotalScans();
        text.setValue("Total de visitas registradas");
    }
    // Puente para la UI
    public LiveData<Integer> getTotalScans() {
        return totalScans;
    }
    public LiveData<String> getText() {
        return text;
    }
}