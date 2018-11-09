package com.example.android.svapliquid.Activity.Ordin.UI.recordUI;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;

/**
 * Created by Andorid on 09/11/2017.
 */

public class OrdineRecordUI extends RecordUI<OrdineRecord> {
    final private PrezzoData prezzoCalcolato;
    public OrdineRecordUI(OrdineRecord object, PrezzoData prezzoCalcolato) {
        super(object);
        this.prezzoCalcolato = prezzoCalcolato;
    }



    public int getId() {
        return getItem().getId();
    }

    public String getNome() {
        return getItem().getNome();
    }

    public String getStato() {
        return getItem().getStato();
    }

    public PrezzoData getPrezzo() {
        return getItem().getPrezzo();
    }

    public PrezzoData getPrezzoCalcolato() {
        return prezzoCalcolato;
    }

    public String getDateIt() {
        return getItem().getDate().getDateIt();
    }
    public boolean getWarning() {
        return getItem().getWarning();
    }
}
