package com.example.android.svapliquid.Activity.Ordin.record;

import com.example.android.svapliquid.Activity.Ordin.data.DateData;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineGUI;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;
import com.example.android.svapliquid.Activity.databases.CursorS;

/**
 * Created by Andorid on 07/11/2017.
 */

public class OrdineRecord implements Record {
    private final OrdineData ordineData;
    private final int id, idAccount;

    public OrdineRecord(CursorS c) {
        this.ordineData = new OrdineData(c.getString(OrdineKey.NOME), new PrezzoData(c.getDouble(OrdineKey.PREZZO)), c.getString(OrdineKey.STATO), c.getString(OrdineKey.INFORMAZIONI),
                new DateData(c.getDate(OrdineKey.DATA).split("-")));
        this.id = c.getInt(OrdineKey.ID);
        this.idAccount = c.getInt(OrdineKey.ID_ACCOUNT);
    }

    @Override
    public int getId() {
        return id;
    }
    public String getNome() {
        return this.ordineData.getNome();
    }
    public String getStato() {
        return this.ordineData.getStato();
    }
    public String getInformazioni() {
        return this.ordineData.getInformazioni();
    }
    public PrezzoData getPrezzo() {
        return this.ordineData.getPrezzo();
    }
    public DateData getDate() {
        return this.ordineData.getDate();
    }
    public boolean getWarning() {
        return this.ordineData.getWarning();
    }

    @Override
    public String toString() {
        return this.ordineData.toString();
    }

    public OrdineData getData() {
        return ordineData;
    }
}
