package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base;


import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Android on 25/07/2017.
 */

public class BasiT extends Tabella<BaseR> {
    final String TAG = "BasiT - ";
    public static final String NOME_TABELLA = "base";
    public static final String KEY_RIGAID = "idBase";
    public static final String KEY_TIPO = "tipoBase";
    public static final String KEY_MG_SU_ML_NICOTINA = "mgSuMlNicotinaBase";
    public static final String KEY_QUANITA = "quantitaBase";
    public static final String KEY_PREZZO = "prezzoBase";
    public static final String KEY_ML_DISPONIBILI = "mlDisponibiliBase";

    public static final String TIPO_BASE_VG = "VG";
    public static final String TIPO_BASE_PG = "PG";

    @Override
    protected BaseR getRecord(CursorS c) {
        return new BaseR(c.getInt(KEY_RIGAID), c.getString(KEY_TIPO), c.getDouble(KEY_MG_SU_ML_NICOTINA), c.getDouble(KEY_QUANITA), c.getDouble(KEY_ML_DISPONIBILI), c.getDouble(KEY_PREZZO));
    }

    @Override
    protected Tabella<BaseR> getInstance() {
        return new BasiT();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public BaseR getRecordByTipoBase(String tipobase) {
        for (int i=0; i<this.records.size(); i++) {
            if (this.records.get(i).getTipoBase().equalsIgnoreCase(tipobase))  return this.records.get(i);
        }
        return null;
    }

    public BasiT getBasiConNicotina() {
        BasiT basi = new BasiT();
        for (int i=0; i<this.records.size(); i++) {
            if (this.records.get(i).getMgSuMlNicotina() != 0) basi.addRecord(this.records.get(i));
        }
        return basi;
    }
}
