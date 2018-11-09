package com.example.android.svapliquid.Activity.Ordin.controller.ordine;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.OrdineRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineGUI;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.R;

import java.util.ArrayList;

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
        //initialValues.put(OrdineKey.ID, database.count(OrdineKey.NOME_TABELLA)+1);
        initialValues.put(OrdineKey.INFORMAZIONI, ordine.getInformazioni());
        initialValues.put(OrdineKey.NOME, ordine.getNome());
        initialValues.put(OrdineKey.PREZZO, ordine.getPrezzo().getPrezzo());
        initialValues.put(OrdineKey.STATO, ordine.getStato());
        initialValues.put(OrdineKey.ID_ACCOUNT, idAccount);
        database.insert(OrdineKey.NOME_TABELLA, null, initialValues);
        return DB.getUltimoIdInserito(database.getDatabase());
    }
    public boolean update(ArrayList<OrdineController> list, final AppCompatActivity activity, final int idAccount) {
        if (list.size() == 1) {
            final OrdineRecord ordineRecord = list.get(0).getRecord();
            final OrdineGUI ordineGUI = new OrdineGUI(ordineRecord.getData());
            final LayoutInflater inflater = activity.getLayoutInflater();
            final View view = inflater.inflate(R.layout.alert_dialog_add_ordine, null);
            final EditText editText_nome = view.findViewById(R.id.editText_alertDialogAddOrdine_nomeOrdine);
            editText_nome.setText(ordineGUI.getNome());
            final EditText editText_prezzo = view.findViewById(R.id.editText_alertDialogAddOrdine_prezzo);
            editText_prezzo.setText(ordineGUI.getPrezzo());
            final EditText editText_stato = view.findViewById(R.id.editText_alertDialogAddOrdine_stato);
            editText_stato.setText(ordineGUI.getStato());
            final EditText editText_informazioni = view.findViewById(R.id.editText_alertDialogAddOrdine_informazioni);
            editText_informazioni.setText(ordineGUI.getInformazioni());
            final EditText editText_date = view.findViewById(R.id.editText_alertDialogAddOrdine_date);
            editText_date.setText(ordineGUI.getDate());
            final Switch switch_warning = view.findViewById(R.id.switch_alertDialogAddOrdine);
            switch_warning.setChecked(ordineGUI.getWarning());
            editText_date.setEnabled(false);
            new AlertDialog.Builder(activity).setTitle("Aggiungi all'archivio").setView(view).setPositiveButton("SALVA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    String stato = editText_stato.getText().toString();
                    if (switch_warning.isChecked()) {
                        stato = OrdineData.W+stato;
                    }
                    update(ordineRecord.getId(), editText_nome.getText().toString(), editText_informazioni.getText().toString(), new PrezzoData(Double.parseDouble(editText_prezzo.getText().toString())),
                            stato, idAccount);
                    RecordControllers.ControllerGuiController.refresh(OrdineKey.NOME_TABELLA);
                }
            }).setNeutralButton("ANNULLA", null).show();
        } else {
            Toast.makeText(activity, "Carello vuoto", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean update(int idOrdine, String nome, String informazioni, PrezzoData prezzo, String stato, int idAccount) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(OrdineKey.INFORMAZIONI, informazioni);
        initialValues.put(OrdineKey.NOME, nome);
        initialValues.put(OrdineKey.PREZZO, prezzo.getPrezzo());
        initialValues.put(OrdineKey.STATO, stato);
        initialValues.put(OrdineKey.ID_ACCOUNT, idAccount);
        getDatabase().update(OrdineKey.NOME_TABELLA, initialValues, "idOrdine="+idOrdine);
        return true;
    }

    @Override
    public OrdineController getController(CursorS cursorS) {
        return new OrdineController(cursorS);
    }
}
