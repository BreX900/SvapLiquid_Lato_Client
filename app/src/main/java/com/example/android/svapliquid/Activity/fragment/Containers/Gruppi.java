package com.example.android.svapliquid.Activity.fragment.Containers;

import com.example.android.svapliquid.Activity.databases.Tabella;

import java.util.ArrayList;

/**
 * Created by Android on 21/07/2017.
 */

public abstract class Gruppi<E extends Tabella> {
    protected E tabella;
    public Gruppi(E tabella) {
        this.tabella = tabella;
    }
    public ArrayList<Gruppo> getGruppo() {
        ArrayList<Gruppo> gruppi = new ArrayList<>();
        for (int i=0; i<this.tabella.getNumberRecords(); i++) {
            gruppi.add(this.getGruppo(i));
        }
        return gruppi;
    }
    private Gruppo getGruppo (int position) {
        return new Gruppo(this.addIdGroup(position), addStringGroup(position));
    }
    protected abstract String addStringGroup(int position);
    protected abstract int addIdGroup(int position);
}
