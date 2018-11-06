package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Tables;

/**
 * Created by Android on 18/07/2017.
 */

public class TableProduttore extends Tables {
    final String TAG = "TableProduttore - ";
    public static final String NOME_TABELLA = "produttore";
    public static final String KEY_RIGAID = "idProduttore";
    public static final String KEY_NOME = "nomeProduttore";
    public TableProduttore () {}
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(ILog.LOG_TAG, TAG+ "Creazione Tabella");
        db.execSQL("create table " + NOME_TABELLA + " (" + KEY_RIGAID + " integer primary key autoincrement, "
                + KEY_NOME + " text not null);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(ILog.LOG_TAG, TAG+ "Riempimento Tabella");
        this.inserisciProduttore(db, 1, "La Tabaccheria");
        this.inserisciProduttore(db, 2, "FlavourArt");
        this.inserisciProduttore(db, 3, "Capella");
        this.inserisciProduttore(db, 4, "T-Juice");
        this.inserisciProduttore(db, 5, "Vapor Cave");
        this.inserisciProduttore(db, 6, "The Flavour Apprentice (TPA)");
    }
    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public void inserisciProduttore(SQLiteDatabase db, String nome) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        db.insert(NOME_TABELLA, null, initialValues);
    }
    public void inserisciProduttore(SQLiteDatabase db, int id, String nome) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_NOME, nome);
        db.insert(NOME_TABELLA, null, initialValues);
    }
    public Cursor ottieniTuttiProduttori () {
        return DB.getLiquidDB().query(NOME_TABELLA, new String[] {KEY_RIGAID, KEY_NOME}, null, null, null, null, null);
    }
    public Cursor ricercaProduttore(String produttore) {
        // applico il metodo query filtrando per ID
        if (produttore.equalsIgnoreCase("Any")) {
            Log.i(ILog.LOG_TAG, TAG + "ricercaProduttore: Any");
            Cursor cursor = DB.getLiquidDB().query(NOME_TABELLA, null, null, null, null, null, null);
            cursor.moveToFirst();
            Log.i(ILog.LOG_TAG, TAG +"ricercaProduttore: Prova Cursor"+cursor.getString(1));
            return cursor;
        }
        else {
            Cursor mCursore = DB.getLiquidDB().query(true, NOME_TABELLA, new String[]{KEY_RIGAID, KEY_NOME}, KEY_NOME + "=" + produttore, null, null, null, null, null);
            if (mCursore != null) {
                mCursore.moveToFirst();
            }
            return mCursore;
        }
    }

}
