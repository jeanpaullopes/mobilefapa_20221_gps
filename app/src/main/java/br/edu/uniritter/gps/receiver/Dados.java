package br.edu.uniritter.gps.receiver;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.edu.uniritter.gps.gps.adapter.PosicaoViewModel;

public class Dados {
    private static MutableLiveData<List<Location>> dados = new MutableLiveData<>();
    //public static PosicaoViewModel viewmodel;

    public static  MutableLiveData<List<Location>> getDados() {
        if (dados.getValue() == null) {
            dados.setValue(new ArrayList<>());
        }
        return dados;
    }
    public static void gravar(Location loc, float dist) {
        int d = (int) dist;
        Dados.dados.getValue().add(loc);
        dados.setValue(dados.getValue());
        //if (viewmodel != null) {
        //    viewmodel.addLocation(loc);
        //}
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        GeoPoint gp = new GeoPoint(loc.getLatitude(), loc.getLongitude());

        myRef.setValue(new Localizacao(gp));
        */
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("prof");
        GeoPoint gp = new GeoPoint(loc.getLatitude(), loc.getLongitude());

        collection.add(new Localizacao(gp));

    }
}
class Localizacao {
    public GeoPoint localizacao;
    public Date data;

    public Localizacao(GeoPoint localizacao) {
        this.localizacao = localizacao;
        this.data = Calendar.getInstance().getTime();
    }
}
