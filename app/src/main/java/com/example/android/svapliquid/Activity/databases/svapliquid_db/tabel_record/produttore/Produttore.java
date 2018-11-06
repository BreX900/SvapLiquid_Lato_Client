package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Android on 18/07/2017.
 */

public class Produttore extends Record {
    public String nome;
    public Produttore (int id, String nome) {
        super(id);
        this.nome = nome;
    }
    public void setProduttore(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public String getNome() {
        return this.nome;
    }
}
