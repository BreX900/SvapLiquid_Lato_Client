package com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI;

import com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI.ObjectUICollection;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;

import java.util.ArrayList;

/**
 * Created by Andorid on 09/11/2017.
 */

public abstract class RecordUICollection<A extends Record, E extends RecordUI<A>> extends ObjectUICollection<A, E> {
    public RecordUICollection(ArrayList<A> recordCollection) {
        super(recordCollection);
    }
    public void addAll(ArrayList<A> recordCollection) {super.addAll(recordCollection);}
}
