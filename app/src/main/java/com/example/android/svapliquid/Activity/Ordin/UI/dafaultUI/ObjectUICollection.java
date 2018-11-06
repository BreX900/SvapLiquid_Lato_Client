package com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI;

import java.util.ArrayList;

/**
 * Created by Andorid on 09/11/2017.
 */

public abstract class ObjectUICollection<A extends Object, E extends ObjectUI<A>> extends ArrayList<E> {
    public ObjectUICollection(ArrayList<A> recordCollection) {
        addAll(recordCollection);
    }
    public void addAll(ArrayList<A> recordCollection) {
        A[] array = (A[]) recordCollection.toArray();
        for (A tmp: array) {
            add(getContainer(tmp));
        }
    }
    public abstract E getContainer(A object);
}
