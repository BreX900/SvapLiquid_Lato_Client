package com.example.android.svapliquid.Activity.Ordin.data;

import com.example.android.svapliquid.Activity.Utility;

/**
 * Created by Andorid on 09/11/2017.
 */

public class PrezzoData {
    private double prezzo;

    public PrezzoData() {
        this(0);
    }
    public PrezzoData(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return Utility.castDecimal(prezzo, 2);
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
