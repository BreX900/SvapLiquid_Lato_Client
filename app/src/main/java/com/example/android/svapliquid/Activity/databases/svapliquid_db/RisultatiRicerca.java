package com.example.android.svapliquid.Activity.databases.svapliquid_db;

import android.database.Cursor;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Tabella;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aroma.AromiT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.aromaInRicetta.AromaInRicettaT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BasiT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccetteT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria.Categorie;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria.TableCategoria;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea.Linee;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea.TableLinea;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquidi;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquido;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore.Produttori;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore.TableProduttore;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta.RicettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta.RicetteT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TableTipoTiro;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TipoTiri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Android on 19/07/2017.
 */

public class RisultatiRicerca implements Serializable { //*
    static final String TAG = "RisultatiRicerca - ";
    private final Produttori produttori = new Produttori();
    private final Linee linee =  new Linee();
    private final Categorie categorie = new Categorie();
    private final Liquidi liquidi = new Liquidi();
    private final TipoTiri tipotiri = new TipoTiri();

    private final AromiT aromi = new AromiT();
    private final AromaInRicettaT aromiInRicette = new AromaInRicettaT();
    private final RicetteT ricette = new RicetteT();

    private final BasiT basi = new BasiT();
    private final BoccetteT boccette = new BoccetteT();

    public RisultatiRicerca () {}

    public CursorS ricercaTutto (QueryString queryString, ValoriRicerca vr) {
        QueryString q;
        if (queryString==null) q = new QueryString();
        else q = queryString;
        q.addSelect("TT.*");
        q.addFromAs(TableTipoTiro.NOME_TABELLA, "TT");

        boolean adLiquido=false, adCategoria= false, adLinea=false, adProduttore= false;
        if (vr.isSetValue(Liquido.KEY_NOME)) {
            q.searchAddWhereAndLike("L." + Liquido.KEY_NOME, vr.get(Liquido.KEY_NOME));
            adLiquido = true;
        }
        if (vr.isSetValue(TableCategoria.KEY_NOME)) {
            q.searchAddWhereAndLike("C."+TableCategoria.KEY_NOME, vr.get(TableCategoria.KEY_NOME));
            adLiquido = true;
            adCategoria = true;
        }
        if (vr.isSetValue(TableLinea.KEY_NOME)) {
            q.searchAddWhereAndLike("LI." + TableLinea.KEY_NOME, vr.get(TableLinea.KEY_NOME));
            adLiquido = true;
            adCategoria = true;
            adLinea = true;
        }
        if (vr.isSetValue(TableProduttore.KEY_NOME)) {
            q.searchAddWhereAndLike("P."+TableProduttore.KEY_NOME, vr.get(TableProduttore.KEY_NOME));
            adLiquido = true;
            adCategoria = true;
            adLinea = true;
            adProduttore = true;
        }

        if (adLiquido) {
            q.addFromAs(Liquido.NOME_TABELLA, "L");
            q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"=L."+Liquido.KEY_ID_TIPO_TIRO);
            if (adCategoria) {
                q.addFromAs(TableCategoria.NOME_TABELLA, "C");
                q.addWhereAnd("L."+Liquido.KEY_ID_CATEGORIA+"=C."+TableCategoria.KEY_RIGAID);
            }
            if (adLinea) {
                q.addFromAs(TableLinea.NOME_TABELLA, "LI");
                q.addWhereAnd("LI."+TableLinea.KEY_RIGAID+"=L."+Liquido.KEY_ID_LINEA);
                if (adProduttore) {
                    q.addFromAs(TableProduttore.NOME_TABELLA, "P");
                    q.addWhereAnd("P."+TableProduttore.KEY_RIGAID+"=LI."+TableLinea.KEY_ID_PRODUTTORE);
                }
            }
        }

        q.addOrderAsc("TT."+TableTipoTiro.KEY_RIGAID);//.addOrderAsc("C."+TableCategoria.KEY_NOME);
        Log.i(ILog.LOG_TAG, TAG +"setRecords: "+q.toString());
        return new CursorS(DB.getLiquidDB().rawQuery(q.getQuery(), null));
    }
    private void setRecords(QueryString q, Tabella... tabelle) {
        //Log.i(ILog.LOG_TAG, TAG +"setRecords: "+q.getQuery());
        CursorS cursorS = new CursorS(DB.getLiquidDB().rawQuery(q.getQuery(), null));
        this.setRecords(cursorS, tabelle);
    }
    private void setRecords(CursorS c, Tabella... tabelle) {
        for (Tabella tmp: tabelle) {
            tmp.setRecords(c);
        }
    }
    public void ricercaTutto (ValoriRicerca vr) {
        CursorS cursor = this.ricercaTutto(null, vr);
        this.setRecords(cursor, this.tipotiri);
    }
    public Produttori getProduttori() {
        return this.produttori;
    }

    public void ricercaCategoriaLiquidoByTipoTiroAndCategorie(int idTipoTiro, Categorie categorie) {
        QueryString q = new QueryString();
        q.addSelect("C.*");
        q.addFromAs(Liquido.NOME_TABELLA, "L").addFromAs(TableCategoria.NOME_TABELLA, "C").addFromAs(TableTipoTiro.NOME_TABELLA, "TT");
        q.addWhereAnd("L."+Liquido.KEY_ID_CATEGORIA+"=C."+TableCategoria.KEY_RIGAID);
        q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"=L."+Liquido.KEY_ID_TIPO_TIRO);
        q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"="+idTipoTiro);
        if (!categorie.isEmpty()) {
            QueryString s = new QueryString();
            for (int i = 0; i < categorie.getNumberRecords(); i++) {
                s.addWhereOr("C." + TableCategoria.KEY_RIGAID + "=" + categorie.getRecordByPosition(i).getId());
            }
            q.addWhereAnd("(" + s.toString() + ")");
        }
        q.addOrderAsc("C."+TableCategoria.KEY_NOME);
        this.setRecords(q, this.categorie);
    }

    public Categorie getTableCategoria() {
        return this.categorie;
    }
    public Liquidi getLiquidi() {
        return liquidi;
    }

    public void getAllTableByIdLiquid(int idLiquido) {
        QueryString q = new QueryString();
        q.addSelect("*");

        q.addFromAs(TableProduttore.NOME_TABELLA, "P").addFromAs(TableLinea.NOME_TABELLA, "LI").addFromAs(Liquido.NOME_TABELLA, "L")
                .addFromAs(TableCategoria.NOME_TABELLA, "C").addFromAs(TableTipoTiro.NOME_TABELLA, "TT");
        q.addFromAs(AromaInRicettaT.NOME_TABELLA, "AR").addFromAs(AromiT.NOME_TABELLA, "A").addFromAs(RicetteT.NOME_TABELLA, "R");
        q.addWhereAnd("P."+TableProduttore.KEY_RIGAID+"=LI."+TableLinea.KEY_ID_PRODUTTORE).addWhereAnd("LI."+TableLinea.KEY_RIGAID+"=L."+Liquido.KEY_ID_LINEA);
        q.addWhereAnd("L."+Liquido.KEY_ID_CATEGORIA+"=C."+TableCategoria.KEY_RIGAID);
        q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"=L."+Liquido.KEY_ID_TIPO_TIRO);
        q.addWhereAnd("L."+Liquido.KEY_RIGAID+"=R."+RicetteT.KEY_ID_LIQUIDO).addWhereAnd("R."+RicetteT.KEY_RIGAID+"=AR."+AromaInRicettaT.KEY_ID_RICETTA)
                .addWhereAnd("AR."+AromaInRicettaT.KEY_ID_AROMA+"=A."+ AromiT.KEY_RIGAID);

        q.addWhereAnd("L."+Liquido.KEY_RIGAID+"="+idLiquido);

        this.setRecords(q, this.liquidi, this.categorie, this.tipotiri, this.linee, this.produttori, this.aromi, this.aromiInRicette, this.ricette);
        this.setTabell(this.basi, this.boccette);

    }

    private void setTabell(Tabella... tabelle) {
        for (Tabella tmp: tabelle) {
            QueryString q = new QueryString();
            q.addSelectAll();
            q.addFrom(tmp.getNomeTabella());
            this.setRecords(q, tmp);
        }
    }
    public void setBasiAndBoccette() {
        this.setTabell(this.basi, this.boccette);
    }

    public TipoTiri getTipoTiri() {
        return this.tipotiri;
    }

    public Linee getLinee() {
        return linee;
    }

    public BasiT getBasi() {
        return this.basi;
    }

    public AromiT getAromi() {
        return this.aromi;
    }

    public AromaInRicettaT getAromiInRicette() {
        return this.aromiInRicette;
    }

    public double getCostoRicetta(int position, double mlProdotto) {
        RicettaR ricetta = this.ricette.getRecordByPosition(position);
        Log.i(ILog.LOG_TAG, TAG +"getCostoRicetta: "+ricetta.getId());
        AromaInRicettaT tabella = this.aromiInRicette.getRecordsByIdAroma(ricetta.getId());
        Log.i(ILog.LOG_TAG, TAG +"getCostoRicetta: "+tabella.getRecordByPosition(0).getId());
        return this.aromi.getPrezzoTotale(tabella, mlProdotto);
    }

    public RicetteT getRicette() {
        return this.ricette;
    }

    public BoccetteT getBoccette() {
        return this.boccette;
    }

    public boolean isEmpty() {
        return aromi.isEmpty() && basi.isEmpty() && ricette.isEmpty() && aromiInRicette.isEmpty() && liquidi.isEmpty() && linee.isEmpty() && categorie.isEmpty() && tipotiri.isEmpty() && produttori.isEmpty();
    }

    public Categorie getTableCategoriaByIdTipoTiro(int idTipoTiro) {
        Categorie cats = new Categorie();
        ArrayList<Integer> idCategorie = new ArrayList<>();
        for (int i=0; i<this.liquidi.getNumberRecords(); i++) {
            if (this.liquidi.getRecordByPosition(i).getIdTipoTiro() == idTipoTiro) {
                if (!idCategorie.contains(this.liquidi.getRecordByPosition(i).getIdCategoria())) {
                    cats.addRecord(this.categorie.getRecordById(this.liquidi.getRecordByPosition(i).getIdCategoria()));
                    idCategorie.add(this.liquidi.getRecordByPosition(i).getIdCategoria());
                }
            }
        }
        return cats;
    }

    public void ricercaTutto(int idTipoTiro, ValoriRicerca valori) {
        QueryString q = new QueryString();
        q.addSelect("C.*");
        q.addFromAs(TableTipoTiro.NOME_TABELLA, "C");

        q.addFromAs(Liquido.NOME_TABELLA, "L").addFromAs(TableTipoTiro.NOME_TABELLA, "TT");
        q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"=L."+Liquido.KEY_ID_TIPO_TIRO).addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"="+idTipoTiro);
        q.addFromAs(TableCategoria.NOME_TABELLA, "C");
        q.addWhereAnd("L."+Liquido.KEY_ID_CATEGORIA+"=C."+TableCategoria.KEY_RIGAID);

        boolean adLinea=false, adProduttore= false;
        if (valori.isSetValue(Liquido.KEY_NOME)) {
            q.searchAddWhereAndLike("L." + Liquido.KEY_NOME, valori.get(Liquido.KEY_NOME));
        }
        if (valori.isSetValue(TableCategoria.KEY_NOME)) {
            q.searchAddWhereAndLike("C."+TableCategoria.KEY_NOME, valori.get(TableCategoria.KEY_NOME));
        }
        if (valori.isSetValue(TableLinea.KEY_NOME)) {
            q.searchAddWhereAndLike("LI." + TableLinea.KEY_NOME, valori.get(TableLinea.KEY_NOME));
            adLinea = true;
        }
        if (valori.isSetValue(TableProduttore.KEY_NOME)) {
            q.searchAddWhereAndLike("P."+TableProduttore.KEY_NOME, valori.get(TableProduttore.KEY_NOME));
            adLinea = true;
            adProduttore = true;
        }


        if (adLinea) {
            q.addFromAs(TableLinea.NOME_TABELLA, "LI");
            q.addWhereAnd("LI." + TableLinea.KEY_RIGAID + "=L." + Liquido.KEY_ID_LINEA);
            if (adProduttore) {
                q.addFromAs(TableProduttore.NOME_TABELLA, "P");
                q.addWhereAnd("P." + TableProduttore.KEY_RIGAID + "=LI." + TableLinea.KEY_ID_PRODUTTORE);
            }
        }

        //q.addOrderAsc("TT."+TableTipoTiro.KEY_RIGAID).addOrderAsc("C."+TableCategoria.KEY_NOME);
        this.setRecords(q, this.categorie);
    }

    public void ricercaTutto(int idTipoTiro, int idCategoria, ValoriRicerca valori) {
        QueryString q = new QueryString();
        q.addSelect("L.*");
        q.addFromAs(TableTipoTiro.NOME_TABELLA, "C");

        q.addFromAs(Liquido.NOME_TABELLA, "L").addFromAs(TableTipoTiro.NOME_TABELLA, "TT");
        q.addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"=L."+Liquido.KEY_ID_TIPO_TIRO).addWhereAnd("TT."+TableTipoTiro.KEY_RIGAID+"="+idTipoTiro);
        q.addFromAs(TableCategoria.NOME_TABELLA, "C");
        q.addWhereAnd("L."+Liquido.KEY_ID_CATEGORIA+"=C."+TableCategoria.KEY_RIGAID).addWhereAnd("C."+TableCategoria.KEY_RIGAID+"="+idCategoria);

        boolean adLinea=false, adProduttore= false;
        if (valori.isSetValue(Liquido.KEY_NOME)) {
            q.searchAddWhereAndLike("L." + Liquido.KEY_NOME, valori.get(Liquido.KEY_NOME));
        }
        if (valori.isSetValue(TableCategoria.KEY_NOME)) {
            q.searchAddWhereAndLike("C."+TableCategoria.KEY_NOME, valori.get(TableCategoria.KEY_NOME));
        }
        if (valori.isSetValue(TableLinea.KEY_NOME)) {
            q.searchAddWhereAndLike("LI." + TableLinea.KEY_NOME, valori.get(TableLinea.KEY_NOME));
            adLinea = true;
        }
        if (valori.isSetValue(TableProduttore.KEY_NOME)) {
            q.searchAddWhereAndLike("P."+TableProduttore.KEY_NOME, valori.get(TableProduttore.KEY_NOME));
            adLinea = true;
            adProduttore = true;
        }


        if (adLinea) {
            q.addFromAs(TableLinea.NOME_TABELLA, "LI");
            q.addWhereAnd("LI." + TableLinea.KEY_RIGAID + "=L." + Liquido.KEY_ID_LINEA);
            if (adProduttore) {
                q.addFromAs(TableProduttore.NOME_TABELLA, "P");
                q.addWhereAnd("P." + TableProduttore.KEY_RIGAID + "=LI." + TableLinea.KEY_ID_PRODUTTORE);
            }
        }
        Cursor cursor = DB.getLiquidDB().rawQuery(q.getQuery(), null);
        //q.addOrderAsc("TT."+TableTipoTiro.KEY_RIGAID).addOrderAsc("C."+TableCategoria.KEY_NOME);
        this.setRecords(q, this.liquidi);
    }
}
/*
* 1
*
* 1     1 2 3
*
* */