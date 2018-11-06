package com.example.android.svapliquid.Activity.databases.account_db.prodotto;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquido;

import java.io.Serializable;

/**
 * Created by Andorid on 03/08/2017.
 */

public class ProdottoR extends Record implements Serializable {
    public final static String NOME_TABELLA = "prodotto";

    public final static String KEY_RIGAID = "idProdotto";
    public final static String KEY_ID_LIQUIDO = "idLiquido";
    public final static String KEY_NICOTINA = "nicotinaProdotto";
    public final static String KEY_ID_BOCCETTA = "idBoccetta";
    public final static String KEY_ID_PREZZO = "prezzoProdotto";
    public final static String KEY_ID_ORDINE = "idOrdine";

    private final Liquido liquido;
    private double nicotina;
    private final BoccettaR boccetta;
    private final int idOrdine;
    //private final BaseR base, baseNicotinata;


    public ProdottoR(CursorS c, SQLiteDatabase db1) {
        this(c.getInt(KEY_RIGAID), new Liquido(c.getInt(KEY_ID_LIQUIDO), db1), c.getDouble(KEY_NICOTINA), new BoccettaR(c.getInt(KEY_ID_BOCCETTA), db1), c.getInt(KEY_ID_ORDINE));
    }
    public ProdottoR(int id, Liquido liquido, double nicotina, BoccettaR boccetta) {
        this(id, liquido, nicotina, boccetta, 0);
    }
    public ProdottoR(int id, Liquido liquido, double nicotina, BoccettaR boccetta, int idOrdine) {
        super(id);
        this.liquido = liquido;
        this.nicotina = nicotina;
        this.boccetta = boccetta;
        this.idOrdine = idOrdine;
    }
    public ProdottoR(String s, int idOrdine) {
        super(0);
        String[] prodotto = s.split("-");
        this.liquido = Liquido.getRecordById(Integer.valueOf(prodotto[0]));
        this.nicotina = Double.valueOf(prodotto[1]);
        this.boccetta = BoccettaR.getRecordById(Integer.valueOf(prodotto[2]));
        this.idOrdine = idOrdine;
    }

    public ProdottoR(int idLiquido) {
        this(getRecordByInt(DB.getLiquidDB(), NOME_TABELLA, KEY_RIGAID, idLiquido), DB.getLiquidDB());
    }

    public Liquido getLiquido() {
        return this.liquido;
    }

    public double getNicotina() {
        return this.nicotina;
    }

    public BoccettaR getBoccetta() {
        return this.boccetta;
    }

    public String getMessage() {
        return this.liquido.getId()+"-"+this.nicotina+"-"+this.boccetta.getId();
    }

    public void putDB(SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        if (this.id != 0) initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_ID_LIQUIDO, liquido.getId());
        initialValues.put(KEY_NICOTINA, nicotina);
        initialValues.put(KEY_ID_BOCCETTA, boccetta.getId());
        initialValues.put(KEY_ID_ORDINE, idOrdine);
        db.insert(NOME_TABELLA, null, initialValues);
        if (this.getId() ==0) {
            this.id = DB.getUltimoIdInserito(db);
        }
    }


    public void setNicotina(double nicotina) {
        this.nicotina = nicotina;
    }
}
