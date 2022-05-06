package br.edu.uniritter.mobile.buscasensores.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.uniritter.mobile.buscasensores.R;
import br.edu.uniritter.mobile.buscasensores.viewmodel.SensorsViewModel;
import br.edu.uniritter.mobile.views.GPSActivity;

public class MainActivity extends AppCompatActivity {

    private SensorsViewModel viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewmodel = new ViewModelProvider(this).get(SensorsViewModel.class);
        viewmodel.setContexto(this);
        viewmodel.getSensores().observe(this, new Observer<List<Sensor>>() {
            @Override
            public void onChanged(List<Sensor> sensors) {
                LinearLayout layout = findViewById(R.id.layoutSensores);
                layout.removeAllViews();
                for (Sensor sensor : sensors) {
                    TextView tv = new TextView(getBaseContext());
                    tv.setText(sensor.getName());
                    layout.addView(tv);
                    ;
                }

            }
        });

        findViewById(R.id.btNext).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
            startActivity(intent);
        });

    }
}