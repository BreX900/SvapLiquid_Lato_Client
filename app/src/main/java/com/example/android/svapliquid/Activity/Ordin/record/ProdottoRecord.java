package com.example.android.svapliquid.Activity.Ordin.record;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.data.ProdottoData;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;

/**
 * Created by Andorid on 09/11/2017.
 */

public class ProdottoRecord implements Record {
    final private int id, idOrdine;
    final ProdottoData prodotto;
    public ProdottoRecord(CursorS c) {
        this.prodotto = new ProdottoData(c.getString(ProdottoKey.NOME_PRODOTTO), c.getString(ProdottoKey.COMPOSIZIONE), c.getDouble(ProdottoKey.NICOTINA), c.getInt(ProdottoKey.ML),
                c.getString(ProdottoKey.BOCCETTA), c.getPrezzo(ProdottoKey.PREZZO));
        this.id = c.getInt(ProdottoKey.ID);
        this.idOrdine = c.getInt(ProdottoKey.ID_ORDINE);
    }

    @Override
    public int getId() {
        return id;
    }
    public int getIdOrdine() {
        return idOrdine;
    }
    public String getNome() {
        return prodotto.getNome();
    }
    public String getComposizione() {
        return prodotto.getComposizione();
    }
    public String getBoccetta() {
        return prodotto.getBoccetta();
    }
    public int getMl() {
        return prodotto.getMl();
    }
    public double getNicotina() {
        return prodotto.getNicotina();
    }
    public PrezzoData getPrezzoData() {
        return prodotto.getPrezzoData();
    }

    public static int putDatabase(SQLiteDatabase db, int idOrdine, ProdottoData prodottoData) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ProdottoKey.NOME_PRODOTTO, prodottoData.getNome());
        initialValues.put(ProdottoKey.COMPOSIZIONE, prodottoData.getComposizione());
        initialValues.put(ProdottoKey.NICOTINA, prodottoData.getNicotina());
        initialValues.put(ProdottoKey.BOCCETTA, prodottoData.getBoccetta());
        initialValues.put(ProdottoKey.ML, prodottoData.getMl());
        initialValues.put(ProdottoKey.PREZZO, prodottoData.getPrezzoData().getPrezzo());
        initialValues.put(ProdottoKey.ID_ORDINE, idOrdine);
        db.insert(ProdottoKey.NOME_TABELLA, null, initialValues);
        return DB.getUltimoIdInserito(db);
    }

    public void deleteDB(SQLiteDatabase db) {
        db.delete(ProdottoKey.NOME_TABELLA, ProdottoKey.ID+"="+this.id, null);
    }
}
