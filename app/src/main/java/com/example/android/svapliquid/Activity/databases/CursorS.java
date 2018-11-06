package com.example.android.svapliquid.Activity.databases;

import android.database.Cursor;

import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;

/**
 * Created by Android on 19/07/2017.
 */

public class CursorS {
    private final Cursor cursor;
    public CursorS (Cursor cursor) {
        this.cursor = cursor;
    }

    public String getString(String nameColumn) {
        return  this.cursor.getString(this.cursor.getColumnIndex(nameColumn));
    }
    public int getInt(String nameColumn) {
        return this.cursor.getInt(this.cursor.getColumnIndex(nameColumn));
    }
    public double getDouble(String nameColumn) {
        return this.cursor.getDouble(this.cursor.getColumnIndex(nameColumn));
    }
    public boolean getBoolean(String nameColumn) {
        return this.cursor.getInt(this.cursor.getColumnIndex(nameColumn)) == 1;
    }

    public String getDate(String nameColumn) {
        return this.getString(nameColumn);
    }
    public PrezzoData getPrezzo(String nameColumn) {
        return new PrezzoData(getDouble(nameColumn));
    }

    public boolean moveToNext() {
        return this.cursor.moveToNext();
    }
    public boolean isAfterLast() {
        return this.cursor.isAfterLast();
    }
    public boolean moveToFirst() {
        return this.cursor.moveToFirst();
    }
}
