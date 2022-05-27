package br.edu.uniritter.gps.gps.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import br.edu.uniritter.gps.receiver.GPSService;
import br.edu.uniritter.atsd.gps.R;

public class LandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1 = new Intent(this, GPSService.class);
        startForegroundService(intent1);
      Log.w("LandActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
    }
}