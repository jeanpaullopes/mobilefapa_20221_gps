package br.edu.uniritter.mobile.buscasensores.viewmodel;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SensorsViewModel extends ViewModel {

    private MutableLiveData<List<String>> listaSensores;
    private MutableLiveData<List<Sensor>> sensores;
    private Context contexto;

    public SensorsViewModel() {
        super();
        listaSensores = new MutableLiveData<List<String>>();
        sensores = new MutableLiveData<List<Sensor>>();


    }

    public MutableLiveData<List<Sensor>> getSensores() {
        return sensores;
    }

    public void setContexto(Context context) {
        this.contexto = context;
        SensorManager sensorManager = (SensorManager) contexto.getSystemService(contexto.SENSOR_SERVICE);
        sensores.setValue( sensorManager.getSensorList(Sensor.TYPE_ALL) );
    }

}
