package br.edu.uniritter.mobile.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class GPSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "GPSBroadcastReceiver";
    private GPSService service;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive: ");
        Intent intent1 = new Intent(context,GPSService.class);
        Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show();
        context.startForegroundService(intent1);
    }
}
