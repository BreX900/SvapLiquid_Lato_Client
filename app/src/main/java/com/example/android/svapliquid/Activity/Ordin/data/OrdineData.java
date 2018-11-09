package com.example.android.svapliquid.Activity.Ordin.data;

import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 04/11/2017.
 */

public class OrdineData extends Data{
    private String nome, stato, informazioni;
    private PrezzoData prezzo;
    private DateData date;
    public final static String W = "§";

    public OrdineData(@NotNull String nome, @NotNull PrezzoData prezzo, @NotNull String stato, @NotNull String informazioni, @NotNull DateData date) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.stato = stato;
        this.informazioni = informazioni;
        this.date = date;
    }

    public OrdineData() {
        this("", new PrezzoData(), "", "");
    }
    public OrdineData(@NotNull String nome, @NotNull double prezzo) {
        this(nome, new PrezzoData(prezzo), "", "");
    }

    public OrdineData(@NotNull String nome, @NotNull PrezzoData prezzo, @NotNull String stato, @NotNull String informazioni) {
        this(nome, prezzo, stato, informazioni, new DateData());
    }

    public OrdineData(double prezzo) {
        this("", new PrezzoData(prezzo), "", "");
    }

    public OrdineData(OrdineRecord or) {
        this(or.getNome(), or.getPrezzo(), or.getStato(), or.getInformazioni(), or.getDate());
        Log.i(ILog.LOG_TAG, "OrdineGUI2"+ or.getNome());
        Log.i(ILog.LOG_TAG, "OrdineGUI3"+ this.getNome());
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }
    public void setInformazioni(String informazioni) {
        this.informazioni = informazioni;
    }
    public void setPrezzo(PrezzoData prezzo) {
        this.prezzo = prezzo;
    }
    public void setDate(DateData date) {
        this.date = date;
    }

    public String getNome() {
        return nome;
    }
    public String getStato() {
        return stato;
    }
    public String getInformazioni() {
        return informazioni;
    }
    public PrezzoData getPrezzo() {
        return prezzo;
    }
    public DateData getDate() {
        return date;
    }


    public boolean getWarning() {
        return this.stato.contains(OrdineData.W);
    }


    @Override
    public String toString() {
        return "{id:"+this.nome+", stato:"+this.stato+", W:"+this.getWarning()+"}";
    }
}
