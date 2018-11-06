package com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI;

import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;
import com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI.ObjectUI;

/**
 * Created by Andorid on 09/11/2017.
 */

public abstract class RecordUI<E extends Record> extends ObjectUI<E>{
    public RecordUI(E object) {
        super(object);
    }
}
