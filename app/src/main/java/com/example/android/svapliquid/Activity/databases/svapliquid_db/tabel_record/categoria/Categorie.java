package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.OnString;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Android on 20/07/2017.
 */

public class Categorie extends Tabella<Categoria> implements OnString {
    public static final String TAG = "Categorie - ";
    public static final String NOME_TABELLA = "categoria";

    @Override
    protected Categoria getRecord(CursorS c) {
        return new Categoria(c.getInt(TableCategoria.KEY_RIGAID), c.getString(TableCategoria.KEY_NOME));
    }

    @Override
    protected Tabella<Categoria> getInstance() {
        return new Categorie();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    @Override
    public String getString(int position) {
        return this.records.get(position).getNome();
    }
}
