package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.Tables;

/**
 * Created by Android on 19/07/2017.
 */

public class TableCategoria extends Tables{
    final String TAG = "TableCategoria - ";
    public static final String NOME_TABELLA = "categoria";
    public static final String KEY_RIGAID = "idCategoria";
    public static final String KEY_NOME = "nomeCategoria";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ILog.LOG_TAG, TAG+ "Creazione tabella");
        db.execSQL("create table " + NOME_TABELLA + " (" + KEY_RIGAID + " integer primary key autoincrement, "
                + KEY_NOME + " text not null);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersione, int newVersion) {
        Log.i(ILog.LOG_TAG, TAG+ "Riempimento tabella");
        inserisci(db, 1, "Cremosi");
        inserisci(db, 2, "Tabaccosi");
        inserisci(db, 3, "Fruttati");
        inserisci(db, 4, "Mentolati");
    }
    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public void inserisci(SQLiteDatabase db, int id, String nome) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_NOME, nome);
        db.insert(NOME_TABELLA, null, initialValues);
    }
    public void inserisci(SQLiteDatabase db, String nome) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        db.insert(NOME_TABELLA, null, initialValues);
    }
}
