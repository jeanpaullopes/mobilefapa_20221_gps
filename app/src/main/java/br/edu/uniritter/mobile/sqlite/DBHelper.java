package br.edu.uniritter.mobile.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BancoDeDados";
    private static final Integer DB_VERSION = 1;

    public DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String stm = "create table alunos (matricula integer primary key, nome text, email text);";
        sqLiteDatabase.execSQL(stm);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {
        String stm = "alter table alunos add fone text;";
        if (oldVersion == 0) {
            sqLiteDatabase.execSQL(stm);
        }
    }
}
