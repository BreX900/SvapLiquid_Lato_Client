package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta;

import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Andorid on 28/07/2017.
 */

public class AromaInRicettaT extends Tabella<AromaInRicettaR> {
    public final static String TAG = "AromaInRicettaT - ";
    public final static String NOME_TABELLA = "aromaInRicetta";
    public static final String KEY_RIGAID = "idAromaInRicetta";
    public static final String KEY_PERCENTUALE = "percentualeAromaInRicetta";
    public static final String KEY_ID_RICETTA = "idRicetta";
    public static final String KEY_ID_AROMA = "idAroma";

    @Override
    protected AromaInRicettaR getRecord(CursorS c) {
        return new AromaInRicettaR(c.getInt(KEY_RIGAID), c.getDouble(KEY_PERCENTUALE), c.getInt(KEY_ID_RICETTA), c.getInt(KEY_ID_AROMA));
    }

    @Override
    protected AromaInRicettaT getInstance() {
        return new AromaInRicettaT();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public AromaInRicettaT getRecordsByIdAroma(int id) {
        AromaInRicettaT tabella = this.getInstance();
        for (int i=0; i<this.records.size(); i++) {
            Log.i(ILog.LOG_TAG, TAG +"getRecordsByIdAroma: "+this.records.get(i).getIdRicetta());
            if (this.records.get(i).getIdRicetta() == id) tabella.addRecord(this.records.get(i));
        }
        return tabella;
    }
}