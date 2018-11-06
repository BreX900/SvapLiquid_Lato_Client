package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Android on 20/07/2017.
 */

public class TipoTiro extends Record {
    public String nome;

    public TipoTiro(int id, String nome) {
        super(id);
        this.nome = nome;
    }
    public void setLiquido(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public String getNome() {
        return this.nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((TipoTiro) obj).getId();
    }
}
