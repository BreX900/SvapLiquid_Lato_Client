package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Android on 19/07/2017.
 */

public class Linee extends Tabella<Linea> {
    public static final String TAG = "Linee - ";
    public static final String NOME_TABELLA = "linea";

    @Override
    protected Linea getRecord(CursorS c) {
        return new Linea(c.getInt(TableLinea.KEY_RIGAID), c.getString(TableLinea.KEY_NOME), c.getInt(TableLinea.KEY_ID_PRODUTTORE));
    }

    @Override
    protected Tabella<Linea> getInstance() {
        return new Linee();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }
}
