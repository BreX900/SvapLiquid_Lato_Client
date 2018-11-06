package com.example.android.svapliquid.Activity.Ordin.controller.ordine;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.OrdineRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordController;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;

/**
 * Created by Andorid on 12/11/2017.
 */

public class OrdineController extends RecordController<OrdineRecord, OrdineRecordUI> {

    public final static String KEY_PREZZO_CALCOLATO = "prezzoCalcolato";

    OrdineController (CursorS cursorS) {
        super(new OrdineRecordUI(new OrdineRecord(cursorS), cursorS.getPrezzo(KEY_PREZZO_CALCOLATO)));
    }
}
