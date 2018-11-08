package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria;

import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Android on 20/07/2017.
 */

public class Categoria extends Record {
    public String nome;
    public Categoria (int id, String nome) {
        super(id);
        this.nome = nome;
    }
    public void setCategoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public String getNome() {
        return this.nome;
    }
    @Override
    public boolean equals(Object obj) {
        Categoria categoria = (Categoria) obj;
        if (this.getNome().equals(categoria.getNome()))
            if (this.getId() == categoria.getId())
                return true;
        return false;
    }

    @Override
    public String getString() {
        return getNome();
    }
}
