package com.example.android.svapliquid.Activity.databases.account_db;

import android.content.Context;
import android.os.Environment;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Andorid on 04/08/2017.
 */

public class GestioneAccountDB extends SQLiteAssetHelper {
    static final String NOME_DATABASE = "account.db";
    static final int VERSIONE = 1;
    public GestioneAccountDB(Context context) {
        super(context, NOME_DATABASE, Environment.getExternalStorageDirectory().getAbsolutePath()+"/SvapLiquid Ordini", null, VERSIONE);
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
