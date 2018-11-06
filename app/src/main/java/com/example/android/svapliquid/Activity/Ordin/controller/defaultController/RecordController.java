package com.example.android.svapliquid.Activity.Ordin.controller.defaultController;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;

/**
 * Created by Andorid on 12/11/2017.
 */

public abstract class RecordController<RR extends Record, UU extends RecordUI<RR>> extends Controller {
    private final UU recordUI;

    public RecordController(UU recordUI) {
        this.recordUI = recordUI;
    }
    public RR getRecord(){
        return this.recordUI.getItem();
    }
    public UU getRecordUI() {
        return this.recordUI;
    }

}
