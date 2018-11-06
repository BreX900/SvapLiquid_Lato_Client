package com.example.android.svapliquid.Activity.fragment.Containers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 21/07/2017.
 */

public abstract class Partecipanti {
    /*protected E tabella;
    HashMap<Integer, Integer> keys = new HashMap<>();;
    public Partecipanti(E tabella) {
        this.tabella = tabella;
    }*/
    public abstract HashMap<Integer, ArrayList<Partecipante>> getPartecipanti();
    /*private Gruppo getGruppo (int position) {
        return new Gruppo(this.addIdGroup(position), addStringGroup(position));
    }
    protected abstract String addStringGroup(int position);
    protected abstract int addIdGroup(int position);*/
}
