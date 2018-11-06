package com.example.android.svapliquid.Activity.databases.svapliquid_db;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


/**
 * Created by Android on 18/07/2017.
 */

public class GestioneSvapLiquiDB extends SQLiteAssetHelper {
    static final String TAG = "GestioneDB - ";
    static final String DATABASE_NOME = "svapliquid.db";
    static final int DATABASE_VERSIONE = 1;

    public GestioneSvapLiquiDB(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSIONE);
    }
}
