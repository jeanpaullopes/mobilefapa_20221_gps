package br.edu.uniritter.gps.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import br.edu.uniritter.atsd.gps.R;
import br.edu.uniritter.gps.repositorios.PosicaoRepository;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setBuildingsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setIndoorEnabled(true);
        googleMap.setMyLocationEnabled(true);
        PosicaoRepository.getInstance().getPosicoes().observe(this,
                new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {
                        if (locations.size() > 0) {
                            onNewPoint(locations);
                        }
                    }
        });


    }
    private void onNewPoint(List<Location> locs) {
        googleMap.clear();
        LatLng lastPonto = null;
        PolylineOptions plo = new PolylineOptions();
        for (Location loc : locs) {
            LatLng ponto = new LatLng(loc.getLatitude(), loc.getLongitude());
            googleMap.addCircle(new CircleOptions()
                    .center(ponto)
                    .radius(loc.getAccuracy())
                    .strokeColor(Color.RED)
                    .strokeWidth(1));
            int veloc = (int) (loc.getSpeed()*10);
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dot))
                    .position(ponto)
                    .title("+/- "+loc.getAccuracy()+"m  "+(veloc/10.0)+"m/s"));
            plo.add(ponto);

            lastPonto = ponto;
    }
        googleMap.addPolyline(plo);
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(lastPonto));
    }
}