package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Android on 25/07/2017.
 */

public class BaseR extends Record implements Cloneable {
    String tipoBase;
    double mgSuMlNicotina, quantita, mlDisponibili, prezzo;
    public BaseR(int id, String tipoBase, double mgSuMlNicotina, double quantita, double mlDisponibili, double prezzo) {
        super(id);
        this.tipoBase = tipoBase;
        this.mgSuMlNicotina = mgSuMlNicotina;
        this.quantita = quantita;
        this.mlDisponibili = mlDisponibili;
        this.prezzo = prezzo;
    }

    public String getTipoBase() {
        return this.tipoBase;
    }

    public double getPrezzo() {
        return this.prezzo;
    }

    public double getQuantita() {
        return this.quantita;
    }

    public double getMgSuMlNicotina() {
        return this.mgSuMlNicotina;
    }
}
