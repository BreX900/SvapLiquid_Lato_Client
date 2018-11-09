package com.example.android.svapliquid.Activity.Ordin.controller.prodotto;

import android.content.ContentValues;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.ProdottoRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.controller.ordine.OrdiniController;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.data.ProdottiData;
import com.example.android.svapliquid.Activity.Ordin.data.ProdottoData;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.ProdottoRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

import java.util.ArrayList;

/**
 * Created by Andorid on 12/11/2017.
 */

public class ProdottiControllers extends RecordControllers<ProdottoRecord, ProdottoRecordUI, ProdottoController> {
    public ProdottiControllers(DB.Database database, QueryString queryString) {
        super(database, queryString, ProdottoKey.NOME_TABELLA);
    }

    @Override
    public ProdottoController getController(CursorS cursorS) {
        return new ProdottoController(cursorS);
    }

    @Override
    public boolean delete(int position) {
        return delete(getDatabase(), getRecord(position).getId());
    }

    public static boolean delete(DB.Database database, int id) {
        Log.i(ILog.LOG_TAG, "delete delete "+id);
        database.delete(ProdottoKey.NOME_TABELLA, ProdottoKey.ID+"="+id, null);
        return true;
    }

    public static boolean create(DB.Database database, String nome, String composizione, double nicotina, String nomeBoccetta, int ml, PrezzoData prezzo, int idOrdine) {
        ContentValues initialValues = new ContentValues();
        //initialValues.put(ProdottoKey.ID, database.count(ProdottoKey.NOME_TABELLA)+1);
        initialValues.put(ProdottoKey.NOME_PRODOTTO, nome);
        initialValues.put(ProdottoKey.COMPOSIZIONE, composizione);
        initialValues.put(ProdottoKey.NICOTINA, nicotina);
        initialValues.put(ProdottoKey.BOCCETTA, nomeBoccetta);
        initialValues.put(ProdottoKey.ML, ml);
        initialValues.put(ProdottoKey.PREZZO, prezzo.getPrezzo());
        initialValues.put(ProdottoKey.ID_ORDINE, idOrdine);
        database.insert(ProdottoKey.NOME_TABELLA, null, initialValues);
        return true;
    }
    public static boolean create(DB.Database database, ProdottoData prodottoData, int idOrdine) {
        return create(database, prodottoData.getNome(), prodottoData.getComposizione(), prodottoData.getNicotina(), prodottoData.getBoccetta(), prodottoData.getMl(),
                prodottoData.getPrezzoData(), idOrdine);
    }
    public static boolean create(DB.Database database, ProdottiData prodottiData, int idOrdine) {
        for (int i=0; i<prodottiData.size(); i++) {
            create(database, prodottiData.get(i).getNome(), prodottiData.get(i).getComposizione(), prodottiData.get(i).getNicotina(), prodottiData.get(i).getBoccetta(), prodottiData.get(i).getMl(),
                    prodottiData.get(i).getPrezzoData(), idOrdine);
        }
        return true;
    }

    @Override
    public void delete(ArrayList<ProdottoController> itemsDeleted) {
        super.delete(itemsDeleted);
        if (size() == itemsDeleted.size()) {
            OrdiniController.delete(getDatabase(), get(0).getRecord().getIdOrdine());
            ControllerGuiController.refresh(OrdineKey.NOME_TABELLA);
        }
    }
}
