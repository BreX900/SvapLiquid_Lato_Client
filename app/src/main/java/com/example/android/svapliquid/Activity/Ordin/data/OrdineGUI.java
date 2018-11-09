package com.example.android.svapliquid.Activity.Ordin.data;

import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Utility;

public class OrdineGUI extends GUI<OrdineData> {
    protected PrezzoData prezzoCalcolatoData;
    public OrdineGUI(OrdineData ordineData) {
        super(ordineData);
    }

    public OrdineGUI(OrdineData ordineData, PrezzoData prezzoCalcolato) {
        super(ordineData);
        this.prezzoCalcolatoData = prezzoCalcolato;
    }

    public String getNome() {
        Log.i(ILog.LOG_TAG, "OrdineGUI"+ this.data.getNome());
        return this.data.getNome();
    }

    public String getPrezzo() {
        return Utility.castDecimal(this.data.getPrezzo().getPrezzo(), 2)+"";
    }
    public String getPrezzoCalcolato() {
        return Utility.castDecimal(this.prezzoCalcolatoData.getPrezzo(), 2)+"";
    }

    public String getStato() {
        String stato = this.data.getStato();
        if (stato.contains(OrdineData.W)) {
            return stato.replace(OrdineData.W, "");
        } else {
            return stato;
        }
    }

    public String getInformazioni() {
        return this.data.getInformazioni();
    }

    public String getDate() {
        return this.data.getDate().getDateIt();
    }

    public boolean getWarning() {
        return this.data.getWarning();
    }
}
