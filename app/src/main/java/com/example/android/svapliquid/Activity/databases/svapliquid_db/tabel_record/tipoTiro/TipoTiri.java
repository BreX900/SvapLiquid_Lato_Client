package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Android on 20/07/2017.
 */

public class TipoTiri extends Tabella<TipoTiro> {
    public static final String TAG = "TipoTiri - ";
    public static final String NOME_TABELLA = "tipoTiro";

    @Override
    protected TipoTiro getRecord(CursorS c) {
        return new TipoTiro(c.getInt(TableTipoTiro.KEY_RIGAID), c.getString(TableTipoTiro.KEY_NOME));
    }

    @Override
    protected Tabella<TipoTiro> getInstance() {
        return new TipoTiri();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }
}
