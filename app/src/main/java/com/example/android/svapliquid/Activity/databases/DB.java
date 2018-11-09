package com.example.android.svapliquid.Activity.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.account_db.GestioneAccountDB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.GestioneSvapLiquiDB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore.TableProduttore;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Android on 19/07/2017.
 */

public class DB {
    private static final String TAG = "DB - ";
    public static String ULTIMO_ID = "last_insert_rowid()";

    private static Database databaseAccount, databaseLiquid;
    private static AppCompatActivity activity;

    public static void test() {
        Cursor cursor = databaseLiquid.getDatabase().query(TableProduttore.NOME_TABELLA, null, null, null, null, null, null);
        cursor.moveToFirst();
        Log.i(ILog.LOG_TAG, TAG+ databaseLiquid.getDatabase().toString());
        Log.i(ILog.LOG_TAG, TAG +"ricercaProduttore: Prova Cursor"+cursor.getString(1));
    }
    public static int getUltimoIdInserito(SQLiteDatabase db) {
        QueryString queryString = new QueryString(DB.ULTIMO_ID);
        Cursor cursor = db.rawQuery(queryString.getQuery(), null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public static SQLiteDatabase getLiquidDB() {
        return getLiquidDatabase().getDatabase();
    }
    public static SQLiteDatabase getAccountDB() {
        return getAccountDatabase().getDatabase();
    }
    public static Database getLiquidDatabase() {
        if (databaseLiquid == null) DB.databaseLiquid = new DB.Database(new GestioneSvapLiquiDB(activity));
        return databaseLiquid;
    }
    public static Database getAccountDatabase() {
        if (databaseAccount == null) DB.databaseAccount = new DB.Database(new GestioneAccountDB(activity));
        return databaseAccount;
    }

    public static void closeAll() {
        if (databaseAccount != null) {
            databaseAccount.close();
            databaseAccount = null;
        }
        if (databaseLiquid != null) {
            databaseLiquid.close();
            databaseLiquid = null;
        }
        activity = null;
    }
    public static void setContext(AppCompatActivity appCompatActivity) {
        activity = appCompatActivity;
    }


    public static class Database {
        private SQLiteAssetHelper databaseHelper;
        private SQLiteDatabase database;

        public Database(SQLiteAssetHelper databaseHelper){
            this.databaseHelper = databaseHelper;
        }

        public SQLiteDatabase getDatabase() {
            if (database == null) {
                database = databaseHelper.getReadableDatabase();
            }
            else {
                if (!database.isOpen())
                    database = databaseHelper.getReadableDatabase();
            }
            return database;
        }
        private void close() {
            if (database != null) {
                if (database.isOpen())
                    database.close();
            }
            if (database != null) {
                if (database.isOpen())
                    database.close();
            }
        }

        public long insert(String table, String nullColumnHack, ContentValues values) {
            return this.getDatabase().insert(table, nullColumnHack, values);
        }

        public void delete(String table, String whereClause, String[] whereArgs) {
            getDatabase().delete(table, whereClause, whereArgs);
        }

        public void delete(String table, String whereClause) {
            delete(table, whereClause, null);
        }

        public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
            getDatabase().update(table, values, whereClause, whereArgs);
        }
        public void update(String table, ContentValues values, String whereClause) {
            getDatabase().update(table, values, whereClause, null);
        }
        public long count(String table) {
            return DatabaseUtils.queryNumEntries(getDatabase(), table);
        }
    }
}
