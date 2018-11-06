package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aroma;

import android.database.Cursor;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta.AromaInRicettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta.AromaInRicettaT;

/**
 * Created by Andorid on 28/07/2017.
 */

public class AromiT extends Tabella<AromaR>{
    public final static String TAG = "AromiT";
    public final static String NOME_TABELLA = "aroma";
    public final static String KEY_RIGAID = "idAroma";
    public final static String KEY_NOME = "nomeAroma";
    public final static String KEY_COSTO = "costoAroma";
    public final static String KEY_ML_DISPONIBILI = "mlDisponibiliAroma";
    public final static String KEY_ML_BOCCETTA = "mlBoccettaAroma";
    public final static String KEY_ID_LINEA = "idLinea";

    public AromiT() {}
    public AromiT(Cursor c) {
        this(new CursorS(c));
    }
    public AromiT(CursorS c) {
        this.setRecords(c);
    }

    @Override
    protected AromaR getRecord(CursorS c) {
        return new AromaR(c.getInt(KEY_RIGAID), c.getString(KEY_NOME), c.getDouble(KEY_COSTO), c.getDouble(KEY_ML_DISPONIBILI), c.getDouble(KEY_ML_BOCCETTA), c.getInt(KEY_ID_LINEA));
    }

    @Override
    protected Tabella<AromaR> getInstance() {
        return new AromiT();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public double getPrezzoTotale(AromaInRicettaT tabella, double mlProdotto) {
        double costo = 0;
        for (int i=0; i<this.records.size(); i++) {
            Log.i(ILog.LOG_TAG, TAG +"getPrezzoTotale: Primo");
            for (int a=0; a<tabella.getNumberRecords(); a++) {
                Log.i(ILog.LOG_TAG, TAG +"getPrezzoTotale: Secondo");
                if (this.records.get(i).getId() == tabella.getRecordByPosition(a).getIdAroma()) {
                    AromaR aroma = this.records.get(i);
                    AromaInRicettaR arInRi = tabella.getRecordByPosition(a);
                    double mlAroma;
                    Log.i(ILog.LOG_TAG, TAG + "getPrezzoTotale: " + mlProdotto + "--" + arInRi.getPercentuale());
                    mlAroma = ( mlProdotto / 100 * arInRi.getPercentuale());
                    Log.i(ILog.LOG_TAG, TAG + "getPrezzoTotale: mlAroma" + mlAroma + "---" + aroma.getCosto() + "--" + aroma.getMlBoccetta());
                    costo += (aroma.getCosto() / aroma.getMlBoccetta() * mlAroma);
                    Log.i(ILog.LOG_TAG, TAG + "getPrezzoTotale: " + costo);
                }
            }
        }
        return costo;
    }
}
