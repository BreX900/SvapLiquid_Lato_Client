package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Andorid on 28/07/2017.
 */

public class AromaInRicettaR extends Record{
    private double percentuale;
    private int idRicetta;
    private int idAroma;
    public AromaInRicettaR(int id, double percentuale, int idRicetta, int idAroma) {
        super(id);
        this.percentuale = percentuale;
        this.idRicetta = idRicetta;
        this.idAroma = idAroma;
    }

    public double getPercentuale() {
        return percentuale;
    }

    public int getIdAroma() {
        return idAroma;
    }

    public int getIdRicetta() {
        return idRicetta;
    }
}
