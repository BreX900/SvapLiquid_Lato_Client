package com.example.android.svapliquid.Activity.databases.account_db.ordine;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;

/**
 * Created by Andorid on 04/08/2017.
 */

public class OrdineR extends Record {
    public static final String NOME_TABELLA = "ordine";

    public final static String KEY_RIGAID = "idOrdine";
    public final static String KEY_NOME = "nomeOrdine";
    public final static String KEY_ID_ACCOUNT = "idAccount";

    private final String nome;
    private final int idAccount;

    public OrdineR(int id, String nome, int idAccount) {
        super(id);
        this.nome = nome;
        this.idAccount = idAccount;
    }
    public OrdineR(CursorS c) {
        this(c.getInt(KEY_RIGAID), c.getString(KEY_NOME), c.getInt(KEY_ID_ACCOUNT));
    }

    public String getNome() {
        return this.nome;
    }

    public void putDB(SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        if (this.id != 0)   initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_ID_ACCOUNT, idAccount);
        db.insert(NOME_TABELLA, null, initialValues);
        if (this.getId() ==0) {
            this.id = DB.getUltimoIdInserito(db);
        }
    }
    public OrdineR getOrdineByNome(String nome, SQLiteDatabase db) {
        return new OrdineR(getRecordByString(db, NOME_TABELLA, KEY_NOME, nome));
    }
}
