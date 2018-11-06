package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.OnString;
import com.example.android.svapliquid.Activity.databases.Tabella;

import java.util.ArrayList;

/**
 * Created by Android on 20/07/2017.
 */

public class Liquidi extends Tabella<Liquido> implements OnString{
    public static final String TAG = "liquidi - ";
    ArrayList<Liquido> liquidi = new ArrayList<>();


    @Override
    protected Liquido getRecord(CursorS c) {
        return new Liquido(c);
    }

    @Override
    protected Tabella<Liquido> getInstance() {
        return new Liquidi();
    }

    @Override
    public String getNomeTabella() {
        return Liquido.NOME_TABELLA;
    }

    @Override
    public String getString(int position) {
        return this.records.get(position).getNome();
    }
}
