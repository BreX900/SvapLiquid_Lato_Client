package com.example.android.svapliquid.Activity.Ordin.controller.prodotto;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.ProdottoRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordController;
import com.example.android.svapliquid.Activity.Ordin.record.ProdottoRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;

/**
 * Created by Andorid on 12/11/2017.
 */

public class ProdottoController extends RecordController<ProdottoRecord, ProdottoRecordUI> {
    public ProdottoController(CursorS cursorS) {
        super(new ProdottoRecordUI(new ProdottoRecord(cursorS)));
    }
}
