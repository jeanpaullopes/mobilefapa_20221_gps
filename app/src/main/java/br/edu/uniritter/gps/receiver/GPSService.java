package br.edu.uniritter.gps.receiver;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.tasks.Task;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.edu.uniritter.gps.gps.adapter.PosicaoViewModel;

public class GPSService extends LifecycleService {

    public static final String TAG = "GPService";
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationClient;
    Location lastLoc = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ponto 1");
        if (ActivityCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
       /*
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {

            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i(TAG, "onLocationChanged: " + location);
               // Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                Dados.dados.add(location.toString());

            }
        });
        */

        Log.d(TAG, "onCreate: ponto 2");

        /*
        fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,
                        new CancellationToken() {
                            @Override
                            public boolean isCancellationRequested() {
                                return false;
                            }

                            @NonNull
                            @Override
                            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                                return null;
                            }
                        })
                .addOnSuccessListener( new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "onSuccess current location: "+location);
                    }
                });
        */
        Log.d(TAG, "onCreate: ponto 3");
        NotificationChannel channel2 = new NotificationChannel(
                "UniR",
                "Channel UniRitter",
                NotificationManager.IMPORTANCE_LOW
        );
        channel2.setDescription("This is channel 2");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel2);


        Notification notification =
                new Notification.Builder(this, "UniR")
                        .setContentTitle("Titulo")
                        .setContentText("Texto")
                        .build();

// Notification ID cannot be 0.
        //if (intent.getBooleanExtra("boot", false)) {
        Log.d(TAG, "onStartCommand: dando o start");
        startForeground(1234, notification);
        //}

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Toast.makeText(GPSService.this, "onStartCommand", Toast.LENGTH_LONG).show();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest currentLocationRequest = LocationRequest.create();
        currentLocationRequest.setInterval(5000)
                .setMaxWaitTime(10000)
                .setSmallestDisplacement(0)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return Service.START_NOT_STICKY;
        }
        Task<Void> voidTask = fusedLocationClient.requestLocationUpdates(currentLocationRequest,
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        float distancia = 0;
                        Location loc = locationResult.getLastLocation();
                        /*
                        BigDecimal bd = new BigDecimal(loc.getLatitude());
                        BigDecimal latR = bd.setScale(4, RoundingMode.FLOOR);
                        bd = new BigDecimal(loc.getLongitude());
                        BigDecimal lonR = bd.setScale(4, RoundingMode.FLOOR);
                        loc.setLatitude(latR.doubleValue());
                        loc.setLongitude(lonR.doubleValue());

                        Log.d(TAG, "result");
                        for(Location l : locationResult.getLocations()) {
                            Log.d(TAG, "->"+l);

                        }
                        */
                        if (lastLoc != null) {
                            distancia = loc.distanceTo(lastLoc);
                        } else {
                            lastLoc = loc;
                        }
                        Dados.gravar(loc, distancia);
                        Log.d(TAG, "onLocationResult: gravei");
                        if (distancia > loc.getAccuracy()) {
                            lastLoc = loc;
                        }

                       // Log.w(TAG, "onLocationResult: " + distancia + "m acc:" + loc.getAccuracy());
                        //Toast.makeText(GPSService.this, distancia + "m", Toast.LENGTH_SHORT).show();
                    }
                }, null);

       /*

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,4000,0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                float distancia = 0;
                Location loc = location;
                Dados.dados.add(loc.toString());
                Log.d(TAG, "onLocationResult: vou gravar");
                Dados.gravar(loc);
                Log.d(TAG, "onLocationResult: gravei");
                if (lastLoc != null) {
                    distancia = loc.distanceTo(lastLoc);
                }
                if (distancia < loc.getAccuracy()) {
                    distancia = 0;
                }
                lastLoc = loc;
                Log.w(TAG, "onLocationResult: " + distancia + "m acc:" + loc.getAccuracy());
                Toast.makeText(GPSService.this, distancia + "m acc: "+ loc.getAccuracy(), Toast.LENGTH_SHORT).show();

            }
        });

        */
        Log.w(TAG, "onStartCommand ");




        if (intent == null) {
            return START_NOT_STICKY;
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
