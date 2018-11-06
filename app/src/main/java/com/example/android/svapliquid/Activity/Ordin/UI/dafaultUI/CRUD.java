package com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI;

/**
 * Created by Andorid on 11/11/2017.
 */

public interface CRUD<A extends Object> {
    A create();
    A read();
    A update();
    boolean delete();
}
