package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.Tables;

/**
 * Created by Android on 19/07/2017.
 */

public class TableTipoTiro extends Tables {
    final String TAG = "TableTipoTiro - ";
    public static final String NOME_TABELLA = "tipoTiro";
    public static final String KEY_RIGAID = "idTipoTiro";
    public static final String KEY_NOME = "nomeTipoTiro";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ILog.LOG_TAG, TAG+ "Creazione tabella");
        db.execSQL("create table " + NOME_TABELLA + " (" + KEY_RIGAID + " integer primary key autoincrement, "
                + KEY_NOME + " text not null);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersione, int newVersion) {
        Log.i(ILog.LOG_TAG, TAG+ "Riempimento tabella");
        inserisci(db, 1, "Polmone");
        inserisci(db, 2, "Any");
        inserisci(db, 3, "Guancia");
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
