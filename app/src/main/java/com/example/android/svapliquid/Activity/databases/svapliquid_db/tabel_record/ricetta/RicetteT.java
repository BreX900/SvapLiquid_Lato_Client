package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Andorid on 28/07/2017.
 */

public class RicetteT extends Tabella<RicettaR> {
    public final static String NOME_TABELLA = "ricetta";
    public final static String KEY_RIGAID = "idRicetta";
    public final static String KEY_NOME = "nomeRicetta";
    public final static String KEY_COMPOSIZIONE = "composizioneRicetta";
    public final static String KEY_EXTRA_PREZZO = "extraPrezzoRicetta";
    public final static String KEY_MATURAZIONE = "maturazioneRicetta";
    public final static String KEY_ID_LIQUIDO = "idLiquido";


    @Override
    protected RicettaR getRecord(CursorS c) {
        return new RicettaR(c.getInt(KEY_RIGAID), c.getString(KEY_NOME), c.getString(KEY_COMPOSIZIONE), c.getDouble(KEY_EXTRA_PREZZO), c.getInt(KEY_MATURAZIONE), c.getInt(KEY_ID_LIQUIDO));
    }

    @Override
    protected Tabella<RicettaR> getInstance() {
        return new RicetteT();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }
}
