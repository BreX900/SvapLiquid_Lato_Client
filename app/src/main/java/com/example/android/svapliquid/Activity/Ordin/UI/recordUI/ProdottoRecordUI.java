package com.example.android.svapliquid.Activity.Ordin.UI.recordUI;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.record.ProdottoRecord;

/**
 * Created by Andorid on 09/11/2017.
 */

public class ProdottoRecordUI extends RecordUI<ProdottoRecord> {
    public ProdottoRecordUI(ProdottoRecord object) {
        super(object);
    }

    public String getComposizione() {
        return getItem().getComposizione();
    }

    public String getNome() {
        return getItem().getNome();
    }

    public double getNicotina() {
        return getItem().getNicotina();
    }

    public String getBoccetta() {
        return getItem().getBoccetta();
    }

    public int getMl() {
        return getItem().getMl();
    }

    public PrezzoData getPrezzoData() {
        return getItem().getPrezzoData();
    }
}
