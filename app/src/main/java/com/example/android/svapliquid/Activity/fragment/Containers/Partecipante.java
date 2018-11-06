package com.example.android.svapliquid.Activity.fragment.Containers;

/**
 * Created by Android on 21/07/2017.
 */

public class Partecipante {
    private final int id;
    private final String string;
    Partecipante(int id, String s) {
        this.id = id;
        this.string = s;
    }
    public int getId() {
        return this.id;
    }
    public String getString() {
        return this.string;
    }
}
