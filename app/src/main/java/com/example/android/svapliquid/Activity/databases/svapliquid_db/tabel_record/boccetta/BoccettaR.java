package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta;

import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;

import java.io.Serializable;

/**
 * Created by Andorid on 03/08/2017.
 */

public class BoccettaR extends Record implements Serializable, Cloneable {
    public final static String NOME_TABELLA = "boccetta";
    public final static String KEY_RIGAID = "idBoccetta";
    public final static String KEY_NOME = "nomeBoccetta";
    public final static String KEY_ML = "mlBoccetta";
    public final static String KEY_PREZZO = "prezzoBoccetta";
    public final static String KEY_DESCRIZIONE = "descrizioneBoccetta";


    private final String nome;
    private final double ml;
    private final double prezzo;
    private final String descrizione;

    public BoccettaR(int id, String nome, double ml, double prezzo, String desczione) {
        super(id);
        this.nome = nome;
        this.ml = ml;
        this.prezzo = prezzo;
        this.descrizione = desczione;
    }
    public BoccettaR(CursorS c) {
        this(c.getInt(KEY_RIGAID), c.getString(KEY_NOME), c.getDouble(KEY_ML), c.getDouble(KEY_PREZZO), c.getString(KEY_DESCRIZIONE));
    }

    public BoccettaR(int id, SQLiteDatabase db) {
        this(getRecordByInt(db, NOME_TABELLA, KEY_RIGAID, id));
    }

    public double getMl() {
        return this.ml;
    }
    public double getPrezzo() {
        return this.prezzo;
    }
    public String getNome() {
        return this.nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public static BoccettaR getRecordById(int idBoccetta) {
        return new BoccettaR(getRecordByInt(DB.getLiquidDB(), NOME_TABELLA, KEY_RIGAID, idBoccetta));
    }

    public String getIdentificativo() {
        return this.getNome()+" - "+this.getMl()+"ml";
    }
}
