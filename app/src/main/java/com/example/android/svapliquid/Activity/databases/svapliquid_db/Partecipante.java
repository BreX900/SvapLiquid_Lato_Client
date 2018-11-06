package com.example.android.svapliquid.Activity.databases.svapliquid_db;

import com.example.android.svapliquid.Activity.databases.Tabella;

import java.util.HashMap;

/**
 * Created by Android on 21/07/2017.
 */

public abstract class Partecipante<E extends Tabella> {
    protected E tabella;
    public Partecipante(E tabella) {
        this.tabella = tabella;
    }
    public HashMap<Integer, String> getGruppo() {
        HashMap<Integer, String> gruppi = new HashMap<>();
        for (int i=0; i<this.tabella.getNumberRecords(); i++) {
            gruppi.put(this.getId(i), this.getString(i));
        }
        return null;
    }
    protected abstract String getString(int position);
    protected abstract int getId(int position);
}
