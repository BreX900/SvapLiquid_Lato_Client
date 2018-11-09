package com.example.android.svapliquid.Activity;

public class Carello {
    final String user;
    final Prodotti prodotti;
    public Carello (String user, Prodotti prodotti){
        this.user = user;
        this.prodotti = prodotti;
    }

    public Carello(String user) throws Exception {
        this.user = user;
        this.prodotti = Prodotti.load("carello_"+this.user);
    }

    public String getUser() {
        return this.user;
    }

    public Prodotti getProdotti() {
        return this.prodotti;
    }
}
