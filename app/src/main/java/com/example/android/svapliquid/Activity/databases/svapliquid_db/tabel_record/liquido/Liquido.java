package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido;

import android.database.sqlite.SQLiteDatabase;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.Record;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea.TableLinea;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TableTipoTiro;

import java.io.Serializable;

/**
 * Created by Android on 20/07/2017.
 */

public class Liquido extends Record implements Serializable, Cloneable {
    public static final String NOME_TABELLA = "liquido";

    public static final String KEY_RIGAID = "idLiquido";
    public static final String KEY_NOME = "nomeLiquido";
    public static final String KEY_DESCRIZIONE = "descrizioneLiquido";
    public static final String KEY_ID_TIPO_TIRO = TableTipoTiro.KEY_RIGAID;
    public static final String KEY_ID_LINEA = TableLinea.KEY_RIGAID;
    public static final String KEY_ID_CATEGORIA = "idCategoria";

    private final String nome;
    private final String descrione;
    private final int idTipoTiro;
    private final int idLinea;
    private final int idCategoria;


    public Liquido(int id, String nome, String d, int idtt, int idl, int idCategoria) {
        super(id);
        this.nome = nome;
        this.descrione = d;
        this.idTipoTiro = idtt;
        this.idLinea = idl;
        this.idCategoria = idCategoria;
    }

    public Liquido(CursorS c) {
        this(c.getInt(KEY_RIGAID), c.getString(KEY_NOME), c.getString(KEY_DESCRIZIONE), c.getInt(KEY_ID_TIPO_TIRO), c.getInt(KEY_ID_LINEA),
                c.getInt(KEY_ID_CATEGORIA));
    }

    public Liquido(int id, SQLiteDatabase db1) {
        this(getRecordByInt(db1, NOME_TABELLA, KEY_RIGAID, id));

    }

    public Liquido(int idLiquido) {
        this(getRecordByInt(DB.getLiquidDB(), NOME_TABELLA, KEY_RIGAID, idLiquido));
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescrione() {
        return descrione;
    }

    public int getIdTipoTiro() {
        return idTipoTiro;
    }

    public int getIdCategoria() {
        return this.idCategoria;
    }

    public static Liquido getRecordById(int idLiquido) {
        return new Liquido(getRecordByInt(DB.getLiquidDB(), NOME_TABELLA, KEY_RIGAID, idLiquido));
    }
    /*public void inserisci(SQLiteDatabase db, int id, String nome, int idTipoTiro, int idLinea) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_RIGAID, id);
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_ID_TIPO_TIRO, idTipoTiro);
        initialValues.put(KEY_ID_LINEA, idLinea);
        db.insert(NOME_TABELLA, null, initialValues);
    }
    public void inserisci(SQLiteDatabase db, String nome, int idTipoTiro, int idLinea) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NOME, nome);
        initialValues.put(KEY_ID_TIPO_TIRO, idTipoTiro);
        initialValues.put(KEY_ID_LINEA, idLinea);
        db.insert(NOME_TABELLA, null, initialValues);
    }*/

    @Override
    public boolean equals(Object obj) {
        Liquido l = (Liquido) obj;
        if (this.getId() == l.getId())
            return true;
        return false;
    }

    @Override
    public String getString() {
        return getNome();
    }
}
