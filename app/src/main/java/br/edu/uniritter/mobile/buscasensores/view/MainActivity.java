package br.edu.uniritter.mobile.buscasensores.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.uniritter.mobile.buscasensores.R;
import br.edu.uniritter.mobile.buscasensores.viewmodel.SensorsViewModel;
import br.edu.uniritter.mobile.receiver.GPSBroadcastReceiver;
import br.edu.uniritter.mobile.sqlite.DBHelper;
import br.edu.uniritter.mobile.views.GPSActivity;

public class MainActivity extends AppCompatActivity {

    private SensorsViewModel viewmodel;
    int valor = 0;
    BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        br = new GPSBroadcastReceiver();
        IntentFilter intf = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        IntentFilter intf1 = new IntentFilter("android.intent.action.BOOT_COMPETED");

        registerReceiver(br, intf);
        registerReceiver(br, intf1);



        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        int qtd = 0;
        if (preferences.contains("qtd")) {
            qtd = preferences.getInt("qtd",0)+1;
        }
        editor.putInt("qtd", qtd);
        editor.commit();
        ((TextView) findViewById(R.id.textViewNome)).setText(""+qtd);

        viewmodel = new ViewModelProvider(this).get(SensorsViewModel.class);
        viewmodel.setContexto(this);

        viewmodel.getNome().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ((TextView) findViewById(R.id.textViewNome)).setText(s);
            }
        });
        viewmodel.getSensores().observe(this, new Observer<List<Sensor>>() {
            @Override
            public void onChanged(List<Sensor> sensors) {
                LinearLayout layout = findViewById(R.id.layoutSensores);
                layout.removeAllViews();
                for (Sensor sensor : sensors) {
                    TextView tv = new TextView(getBaseContext());
                    tv.setText(sensor.getName()+ " "+ sensor.getStringType());
                    layout.addView(tv);
                    ;
                }

            }
        });
        findViewById(R.id.button2).setOnClickListener(view->{
            viewmodel.setNome("Jean Paul "+ valor++);
        });
        findViewById(R.id.btNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), GPSActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(br);
    }
}