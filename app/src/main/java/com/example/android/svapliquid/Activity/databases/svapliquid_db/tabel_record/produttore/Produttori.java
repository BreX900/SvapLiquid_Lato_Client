package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Android on 18/07/2017.
 */

public class Produttori extends Tabella<Produttore> {
    public static final String TAG = "Produttori";
    public static final String NOME_TABELLA = "produttore";

    @Override
    protected Produttore getRecord(CursorS c) {
        return new Produttore(c.getInt(TableProduttore.KEY_RIGAID), c.getString(TableProduttore.KEY_NOME));
    }

    @Override
    protected Tabella<Produttore> getInstance() {
        return new Produttori();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }
}
