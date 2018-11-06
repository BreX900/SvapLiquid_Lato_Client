package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.OnString;
import com.example.android.svapliquid.Activity.databases.Tabella;

/**
 * Created by Andorid on 03/08/2017.
 */

public class BoccetteT extends Tabella<BoccettaR> implements OnString {

    @Override
    protected BoccettaR getRecord(CursorS c) {
        return new BoccettaR(c);
    }

    @Override
    protected Tabella<BoccettaR> getInstance() {
        return new BoccetteT();
    }

    @Override
    public String getNomeTabella() {
        return BoccettaR.NOME_TABELLA;
    }

    public BoccettaR getBoccettaByMl(int mlBoccetta) {
        for (int i=0; i<this.records.size(); i++) {
            if (this.records.get(i).getMl() == mlBoccetta) return this.records.get(i);
        }
        return null;
    }

    @Override
    public String getString(int position) {
        return this.records.get(position).getNome()+": "+this.records.get(position).getMl()+"ml";
    }
}
