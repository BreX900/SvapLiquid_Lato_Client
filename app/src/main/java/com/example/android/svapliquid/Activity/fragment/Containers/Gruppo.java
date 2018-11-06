package com.example.android.svapliquid.Activity.fragment.Containers;

/**
 * Created by Android on 21/07/2017.
 */

public class Gruppo {
    private final int id;
    private final String string;
    Gruppo(int id, String s) {
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
