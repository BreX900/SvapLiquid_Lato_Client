package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta;

import android.database.Cursor;

import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aroma.AromiT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta.AromaInRicettaT;

/**
 * Created by Andorid on 28/07/2017.
 */

public class RicettaR extends Record implements Cloneable{
    private final String nome;
    private final String composizione;
    private final double extraPrezzo;
    private final int maturazione;
    private final int idLiquido;


    public RicettaR(int id, String nome, String composizione, double extraprezzo, int maturazione, int idLiquido) {
        super(id);
        this.nome = nome;
        this.composizione = composizione;
        this.extraPrezzo = extraprezzo;
        this.maturazione = maturazione;
        this.idLiquido = idLiquido;
    }

    public double getExtraPrezzo() {
        return this.extraPrezzo;
    }
    public int getMaturazione() {
        return this.getMaturazione();
    }

    public double getPrezzoAromi(double mlBoccetta) {
        QueryString query = new QueryString();
        query.addSelect("SUM(A."+AromiT.KEY_COSTO+"/"+AromiT.KEY_ML_BOCCETTA+"*("+mlBoccetta+"/100*"+AromaInRicettaT.KEY_PERCENTUALE+")) AS COSTO");
        query.addFromAs(AromiT.NOME_TABELLA, "A").addFromAs(AromaInRicettaT.NOME_TABELLA, "AR").addFromAs(RicetteT.NOME_TABELLA, "R");
        query.addWhereAnd("A."+AromiT.KEY_RIGAID+"=AR."+AromaInRicettaT.KEY_ID_AROMA).addWhereAnd("R."+RicetteT.KEY_RIGAID+"=AR."+AromaInRicettaT.KEY_ID_RICETTA);
        query.addWhereAnd("R."+RicetteT.KEY_RIGAID+"="+getId());
        query.addGroup("R."+RicetteT.KEY_RIGAID);
        Cursor c = DB.getLiquidDB().rawQuery(query.getQuery(), null);
        c.moveToFirst();
        return c.getDouble(0);
    }
    public double getMlAromi(double mlBoccetta) {
        QueryString query = new QueryString();
        query.addSelect("SUM("+mlBoccetta+"/100*"+AromaInRicettaT.KEY_PERCENTUALE+") AS ML");
        query.addFromAs(AromiT.NOME_TABELLA, "A").addFromAs(AromaInRicettaT.NOME_TABELLA, "AR").addFromAs(RicetteT.NOME_TABELLA, "R");
        query.addWhereAnd("A."+AromiT.KEY_RIGAID+"=AR."+AromaInRicettaT.KEY_ID_AROMA).addWhereAnd("R."+RicetteT.KEY_RIGAID+"=AR."+AromaInRicettaT.KEY_ID_RICETTA);
        query.addWhereAnd("R."+RicetteT.KEY_RIGAID+"="+getId());
        query.addGroup("R."+RicetteT.KEY_RIGAID);
        Cursor c = DB.getLiquidDB().rawQuery(query.getQuery(), null);
        c.moveToFirst();
        return c.getDouble(0);
    }

    public String getComposizione() {
        return composizione;
    }
}
