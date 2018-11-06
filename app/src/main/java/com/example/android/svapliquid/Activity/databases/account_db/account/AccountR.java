package com.example.android.svapliquid.Activity.databases.account_db.account;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Andorid on 04/08/2017.
 */

public class AccountR extends Record {
    private final static String TAG = "AccountR - ";
    private final String nome;
    public final static String KEY_RIGAID = "idAccount";
    public final static String KEY_NOME = "nomeAccount";
    public AccountR(int id, String nome) {
        super(id);
        this.nome = nome;
    }
    AccountR(CursorS c) {
        this(c.getInt(KEY_RIGAID), c.getString(KEY_NOME));
    }

    public String getNome() {
        return nome;
    }
    public void putDB(SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        if (this.getId() != 0)   initialValues.put(KEY_RIGAID, this.getId());
        initialValues.put(KEY_NOME, this.getNome());
        db.insert(AccountT.NOME_TABELLA, null, initialValues);
        if (this.getId() ==0) {
            this.id = DB.getUltimoIdInserito(db);
        }
    }

    public void removeDB(SQLiteDatabase db) {
        db.delete(AccountT.NOME_TABELLA, KEY_RIGAID+"="+this.id, null);
        this.setNullId();
    }
}
