package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.Tables;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore.TableProduttore;

/**
 * Created by Android on 19/07/2017.
 */

public class TableLinea extends Tables {
    final String TAG = "TableLinea - ";
    public static final String NOME_TABELLA = "linea";
    public static final String KEY_RIGAID = "idLinea";
    public static final String KEY_NOME = "nomeLinea";
    public static final String KEY_ID_PRODUTTORE = "idProduttore";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ILog.LOG_TAG, TAG+ "Creazione tabella");
        db.execSQL("create table " + NOME_TABELLA + " (" + KEY_RIGAID + " integer primary key autoincrement, "
                + KEY_NOME + " text not null, "+KEY_ID_PRODUTTORE+" integer not null, FOREIGN KEY("+KEY_ID_PRODUTTORE+") REFERENCES "+ TableProduttore.NOME_TABELLA+"("+TableProduttore.KEY_RIGAID+"));");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersione, int newVersion) {
        Log.i(ILog.LOG_TAG, TAG+ "Riempimento tabella");
        inserisciLinea(db, 1, "Estratti di Tabacco", 1);
        inserisciLinea(db, 2, "Miscele Barrique", 1);
        inserisciLinea(db, 3, "Elite", 1);
    }
    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public void inserisciLinea(SQLiteDatabase db, int id, String nome, int produttore) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_ID_PRODUTTORE, produttore);
        db.insert(NOME_TABELLA, null, initialValues);
    }
    public  void estraiDatiCursor() {

    }
}
