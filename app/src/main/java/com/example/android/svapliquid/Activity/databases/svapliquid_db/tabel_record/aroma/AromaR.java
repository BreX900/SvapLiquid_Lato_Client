package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aroma;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Andorid on 28/07/2017.
 */

public class AromaR extends Record {
    private final String nome;
    private final double costo;
    private final double mlDisponibili;
    private final double mlBoccetta;
    private final int idLinea;
    public AromaR(int id, String nomeAroma, double costoAroma, double mlDisponibili, double mlBoccetta, int idLinea) {
        super(id);
        this.nome = nomeAroma;
        this.costo = costoAroma;
        this.idLinea = idLinea;
        this.mlDisponibili = mlDisponibili;
        this.mlBoccetta = mlBoccetta;
    }

    public double getCosto() {
        return this.costo;
    }

    public double getMlBoccetta() {
        return this.mlBoccetta;
    }
}
