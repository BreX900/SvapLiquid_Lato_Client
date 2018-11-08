package com.example.android.svapliquid.Activity.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

import java.io.Serializable;

/**
 * Created by Android on 21/07/2017.
 */

public abstract class Record implements Serializable, OnStringR{
    protected int id = 0;
    public Record(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    protected static CursorS getRecordByInt(SQLiteDatabase database, String nomeTabella, String nomeColonna, int idRecord) {
        QueryString query = new QueryString();
        query.addSelectAll();
        query.addFrom(nomeTabella);
        query.addWhereAnd(nomeTabella+"."+nomeColonna+"="+idRecord);
        Cursor cursor = database.rawQuery(query.getQuery(), null);
        cursor.moveToFirst();
        return new CursorS(cursor);
    }
    protected static CursorS getRecordByString(SQLiteDatabase database, String nomeTabella, String nomeColonna, String s) {
        QueryString query = new QueryString();
        query.addSelectAll();
        query.addFrom(nomeTabella);
        query.addWhereAnd(nomeTabella+"."+nomeColonna+"="+s);
        Cursor cursor = database.rawQuery(query.getQuery(), null);
        cursor.moveToFirst();
        return new CursorS(cursor);
    }

    @Override
    public boolean equals(Object obj) {
        Record r = (Record) obj;
        if (this.id == r.getId())
            return true;
        return false;
    }

    public void setNullId() {
        this.id = 0;
    }

    public boolean isNullId() {
        return id == 0;
    }

    @Override
    public String getString() {
        return "NotImplement";
    }
}
