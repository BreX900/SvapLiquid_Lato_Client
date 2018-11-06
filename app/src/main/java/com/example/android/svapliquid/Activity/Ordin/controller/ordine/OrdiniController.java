package com.example.android.svapliquid.Activity.Ordin.controller.ordine;

import android.content.ContentValues;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.OrdineRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

/**
 * Created by Andorid on 11/11/2017.
 */

public class OrdiniController extends RecordControllers<OrdineRecord, OrdineRecordUI, OrdineController> {
    public OrdiniController(DB.Database database, QueryString queryString) {
        super(database, queryString, OrdineKey.NOME_TABELLA);
    }

    public boolean delete(int position) {
        return delete(getDatabase(), getRecord(position).getId());
    }


    public static boolean delete(DB.Database database, int id) {
        database.delete(ProdottoKey.NOME_TABELLA, ProdottoKey.ID_ORDINE+"="+id, null);
        database.delete(OrdineKey.NOME_TABELLA, OrdineKey.ID+"="+id, null);
        return true;
    }

    public int create(OrdineData ordine, int idAccount) {
        return create(getDatabase(), ordine, idAccount);
    }
    public static int create(DB.Database database, OrdineData ordine, int idAccount) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(OrdineKey.INFORMAZIONI, ordine.getInformazioni());
        initialValues.put(OrdineKey.NOME, ordine.getNome());
        initialValues.put(OrdineKey.PREZZO, ordine.getPrezzo().getPrezzo());
        initialValues.put(OrdineKey.STATO, ordine.getStato());
        initialValues.put(OrdineKey.ID_ACCOUNT, idAccount);
        database.insert(OrdineKey.NOME_TABELLA, null, initialValues);
        return DB.getUltimoIdInserito(database.getDatabase());
    }
    private boolean update(String nome, String informazioni, PrezzoData prezzo, String stato, int idAccount) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(OrdineKey.INFORMAZIONI, informazioni);
        initialValues.put(OrdineKey.NOME, nome);
        initialValues.put(OrdineKey.PREZZO, prezzo.getPrezzo());
        initialValues.put(OrdineKey.STATO, stato);
        initialValues.put(OrdineKey.ID_ACCOUNT, idAccount);
        getDatabase().insert(OrdineKey.NOME_TABELLA, null, initialValues);
        return true;
    }

    @Override
    public OrdineController getController(CursorS cursorS) {
        return new OrdineController(cursorS);
    }
}
