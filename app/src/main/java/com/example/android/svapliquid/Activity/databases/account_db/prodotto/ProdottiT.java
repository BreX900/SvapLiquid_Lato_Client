package com.example.android.svapliquid.Activity.databases.account_db.prodotto;

import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Tabella;

import java.io.Serializable;

/**
 * Created by Andorid on 03/08/2017.
 */

public class ProdottiT extends Tabella<ProdottoR> implements Serializable{
    public static final String TAG = "ProdottiT - ";
    public ProdottiT() {
        super();
    }
    public ProdottiT(String ordine, int idOrdine) {
        super();
        String[] prodotti = ordine.split("!");
        for (int i=0; i<prodotti.length; i++) {
            if (prodotti[i].contains("-"))
                this.records.add(new ProdottoR(prodotti[i], idOrdine));
        }
    }

    @Override
    protected ProdottoR getRecord(CursorS c) {
        return new ProdottoR(c, DB.getLiquidDB());
    }

    @Override
    protected Tabella<ProdottoR> getInstance() {
        return new ProdottiT();
    }

    @Override
    public String getNomeTabella() {
        return null;
    }


    public String getMessage() {
        String m ="";
        for (int i=0; i<this.getNumberRecords(); i++) {
            if (i != 0) {
                m += "!";
            }
            m += this.records.get(i).getMessage();
        }
        return "Inizio Carello !"+m+"!Fine Carello";
    }

    public void putDB(SQLiteDatabase db) {
        for (int i=0; i<this.records.size(); i++) {
            this.records.get(i).putDB(db);
        }
    }




}
