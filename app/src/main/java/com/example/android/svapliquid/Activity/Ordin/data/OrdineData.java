package com.example.android.svapliquid.Activity.Ordin.data;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 04/11/2017.
 */

public class OrdineData {
    private String nome, stato, informazioni;
    private PrezzoData prezzo;
    private DateData date;

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

    public OrdineData(@NotNull String nome, @NotNull PrezzoData prezzo, @NotNull String stato, @NotNull String informazioni) {
        this(nome, prezzo, stato, informazioni, new DateData());
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


}
