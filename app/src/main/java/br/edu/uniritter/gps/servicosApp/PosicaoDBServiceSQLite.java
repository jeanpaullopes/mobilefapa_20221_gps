package br.edu.uniritter.gps.servicosApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.util.List;

import br.edu.uniritter.gps.sqlite.DBHelper;

public class PosicaoDBServiceSQLite implements PosicaoDBServices{
    DBHelper dbHelper;

    public PosicaoDBServiceSQLite(Context context) {
        dbHelper = new DBHelper(context);
    }
    @Override
    public void salvar(Location loc) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        // aqui faz a mágica
        //writableDatabase.execSQL();

    }

    @Override
    public List<Localizacao> getAllLocalizacao() {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoData(long inicio, long fim) {
        return null;
    }

    @Override
    public List<Localizacao> getAllLocalizacaoNaoEnviadas() {
        return null;
    }
}
