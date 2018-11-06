package com.example.android.svapliquid.Activity.databases.account_db.account;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.activity.NavigationActivity;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Tabella;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

import static android.widget.AdapterView.INVALID_POSITION;

/**
 * Created by Andorid on 04/08/2017.
 */
//public final static String KEY_ = "";
public class AccountT extends Tabella<AccountR> {
    public final static String NOME_TABELLA = "account";



    public AccountT() {super();}
    public AccountT(CursorS cursorS) {super(cursorS);}

    public AccountT(SQLiteDatabase db) {super(db);}


    @Override
    protected AccountR getRecord(CursorS c) {
        return new AccountR(c);
    }

    @Override
    protected AccountT getInstance() {
        return new AccountT();
    }

    @Override
    public String getNomeTabella() {
        return NOME_TABELLA;
    }

    public static AccountT getByName(String s, SQLiteDatabase db) {
        QueryString query = new QueryString();
        query.addSelectAll().addFrom(NOME_TABELLA).addWhereAndLikeRestrict(NOME_TABELLA+"."+AccountR.KEY_NOME, s);
        return new AccountT(new CursorS(db.rawQuery(query.getQuery(), null)));
    }


}
