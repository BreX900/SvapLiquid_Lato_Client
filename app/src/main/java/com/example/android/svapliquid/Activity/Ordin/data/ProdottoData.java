package com.example.android.svapliquid.Activity.Ordin.data;

/**
 * Created by Andorid on 09/11/2017.
 */

public class ProdottoData {
    private String nome, composizione, boccetta;
    private int ml;
    private double nicotina;
    private PrezzoData prezzoData;

    public ProdottoData(String nome, String composizione, double nicotina, int ml, String boccetta, PrezzoData prezzoData) {
        this.nome= nome;
        this.composizione = composizione;
        this.nicotina = nicotina;
        this.ml = ml;
        this.boccetta = boccetta;
        this.prezzoData = prezzoData;

    }

    public String getNome() {
        return nome;
    }

    public String getComposizione() {
        return composizione;
    }

    public String getBoccetta() {
        return boccetta;
    }

    public int getMl() {
        return ml;
    }

    public double getNicotina() {
        return nicotina;
    }

    public PrezzoData getPrezzoData() {
        return prezzoData;
    }


}
