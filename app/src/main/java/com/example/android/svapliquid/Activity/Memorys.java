package com.example.android.svapliquid.Activity;

import java.io.Serializable;

/**
 * Created by Android on 11/07/2017.
 */

public class Memorys implements Serializable{
    public static final String KEY_MEMORYS = "Memorys";

    static public Memorys getInstanceLoadFolder() {
        Memorys memorys = new Memorys();

        return memorys;
    }
}
