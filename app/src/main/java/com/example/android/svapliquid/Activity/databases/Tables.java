package com.example.android.svapliquid.Activity.databases;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Android on 18/07/2017.
 */

public abstract class Tables {  //aggiungere la Tabella nella classe GestioneDB nel costruttore
    public abstract void onCreate(SQLiteDatabase db);   //Creo Tabelle
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersione, int newVersion); //inserisco dati o aggiorno tabelle
    public abstract String getNomeTabella();
}
