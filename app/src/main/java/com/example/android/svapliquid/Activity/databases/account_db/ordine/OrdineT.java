package com.example.android.svapliquid.Activity.databases.account_db.ordine;

import android.database.Cursor;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Tabella;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

/**
 * Created by Andorid on 04/08/2017.
 */

public class OrdineT extends Tabella<OrdineR> {


    @Override
    protected OrdineR getRecord(CursorS c) {
        return new OrdineR(c);
    }

    @Override
    protected OrdineT getInstance() {
        return new OrdineT();
    }

    @Override
    public String getNomeTabella() {
        return OrdineR.NOME_TABELLA;
    }

    static public OrdineT getRecordsByIdAccount(int idAccount){
        QueryString queryString = new QueryString();
        queryString.addSelectAll();
        queryString.addFrom(OrdineR.NOME_TABELLA);
        queryString.addWhereAnd(OrdineR.NOME_TABELLA+"."+OrdineR.KEY_ID_ACCOUNT+"="+idAccount);
        Cursor cursor = DB.getAccountDB().rawQuery(queryString.getQuery(), null);
        OrdineT ordine = new OrdineT();
        ordine.setRecords(new CursorS(cursor));
        return ordine;
    }
}
