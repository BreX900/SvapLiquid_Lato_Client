package com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI;

/**
 * Created by Andorid on 09/11/2017.
 */

public abstract class ObjectUI<E extends Object> {
    final private E object;
    public ObjectUI(E object) {
        this.object = object;
    }
    public E getItem(){
        return object;
    }
}
