package com.example.android.svapliquid.Activity.databases;

import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.fragment.Containers.IdString;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Android on 21/07/2017.
 */

public abstract class Tabella<E extends Record> implements Serializable{
    final static String TAG = "Tabella - ";
    protected ArrayList<E> records = new ArrayList<>();

    public Tabella () {}
    public Tabella (CursorS cursorS) {
        setRecords(cursorS);
    }
    public Tabella (SQLiteDatabase db) {
        QueryString query = new QueryString();
        query.addSelectAll().addFrom(getNomeTabella());
        setRecords(new CursorS(db.rawQuery(query.getQuery(), null)));
    }
    public void setRecords(CursorS cursorS) {
        this.records.clear();
        cursorS.moveToFirst();
        while ((!cursorS.isAfterLast())) {
            E record = this.getRecord(cursorS);
            if (!records.contains(record))
                this.records.add(record);
            cursorS.moveToNext();
        }
    }
    public void setRecords(Tabella<E> tabella) {
        this.records.clear();
        this.records.addAll(tabella.records);
    }
    protected abstract E getRecord(CursorS c);
    public E getRecordById(int idCategoria) {
        for (int i=0; i<records.size(); i++) {
            if (this.records.get(i).getId() == idCategoria) return this.records.get(i);
        }
        return null;
    }
    public E getRecordByPosition(int position) {
        return this.records.get(position);
    }
    public int getNumberRecords() {
        return this.records.size();
    }

    public Tabella<E> getRecordsById(int id) {
        Tabella<E> tabella = this.getInstance();
        for (int i=0; i<this.records.size(); i++) {
            if (this.records.get(i).getId() == id) tabella.addRecord(this.records.get(i));
        }
        return tabella;
    }
    public Tabella<E> getRecordsById(Tabella tab) {
        Tabella<E> tabella = this.getInstance();
        for (int i=0; i<this.records.size(); i++) {
            for (int a=0; a<tab.getNumberRecords(); a++) {
                if (this.records.get(i).getId() == tab.getRecordByPosition(a).getId()) tabella.addRecord(this.records.get(i));
            }
        }
        return tabella;
    }
    protected abstract Tabella<E> getInstance();
    public abstract String getNomeTabella();

    public void addRecord(E record) {
        this.records.add(record);
    }
    public boolean isEmpty() {
        return this.records.isEmpty();
    }
    public void clear(){
        this.records.clear();
    }
    public E remove(int index){
        return this.records.remove(index);
    }
    public boolean remove(E record){
        return this.records.remove(record);
    }

    public ArrayList<IdString> getIdStrings(OnString string) {
        ArrayList<IdString> arraylist = new ArrayList<>();
        for (int i=0; i<this.records.size(); i++) {
            arraylist.add(new IdString(this.records.get(i).getId(), string.getString(i)));
        }
        return arraylist;
    }

    public int getPositionById(int id) {
        for (int i=0; i<this.records.size(); i++) {
            if (this.records.get(i).getId() == id)
                return i;
        }
        return -1;
    }
}
