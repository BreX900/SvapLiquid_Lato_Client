package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Android on 19/07/2017.
 */

public class Linea extends Record {
    public String nome;
    public int idProduttore;
    public Linea (int id, String nome, int idProduttore) {
        super(id);
        this.nome = nome;
        this.idProduttore = idProduttore;
    }
    public void setProduttore(int id, String nome, int idProduttore) {
        this.id = id;
        this.nome = nome;
        this.idProduttore = idProduttore;
    }
    public String getNome() {
        return this.nome;
    }
}
