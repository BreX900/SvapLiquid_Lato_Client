package com.example.android.svapliquid.Activity.Ordin.controller.account;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.AccountRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordController;
import com.example.android.svapliquid.Activity.Ordin.data.AccountData;
import com.example.android.svapliquid.Activity.Ordin.index.AccountKey;
import com.example.android.svapliquid.Activity.Ordin.record.AccountRecord;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.fragment.ricerca.SubMainF;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 13/11/2017.
 */

public class AccountController extends RecordController<AccountRecord, AccountRecordUI> {
    public AccountController(CursorS c) {
        super(new AccountRecordUI(new AccountRecord(c)));
    }

    public static AlertDialog.Builder delete(final DB.Database database, final AccountController accountController, MainActivity activity, final NotifyResultDelete notifyResult) {
        return new AlertDialog.Builder(activity).setTitle("Vuoi rimuovere l'account "+accountController.getRecordUI().getNome()+"?")
                .setMessage("Sei sicuro di volere cancellare il seguente account con la perdita definitiva del suo carello?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyResult.notifyResult(delete(database, accountController.getRecord().getId()));
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
    }

    public static boolean delete(DB.Database database, int id) {
        database.delete(AccountKey.NOME_TABELLA, AccountKey.ID+"="+id);
        return  true;
    }

    public static AlertDialog.Builder create(final DB.Database database, final AppCompatActivity activity, @NotNull final String message, final NotifyResultCreate notifyResult) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_add_account, null);
        final EditText editText_name = (EditText) view.findViewById(R.id.editText_alertDialogAddAccount_nameAccount);
        final EditText editText_surname = (EditText) view.findViewById(R.id.editText_alertDialogAddAccount_surnameAccount);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view).setTitle("CREA UN NUOVO ACCOUNT").setMessage(message+"\nInserire nome e cognome dell'account:")
                .setPositiveButton("CREA ACCOUNT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String nameSurname = editText_name.getText().toString().trim()+" "+editText_surname.getText().toString().trim();
                        Log.i(ILog.LOG_TAG, SubMainF.TAG + "onOptionsItemSelected: " + nameSurname);
                        if (!editText_name.getText().toString().isEmpty() && !editText_surname.getText().toString().isEmpty()) {
                            notifyResult.notifyResult(AccountsController.create(database, new AccountData(nameSurname)));
                        } else {
                            Toast.makeText(activity, "INSERIRE NOME E COGNOME", Toast.LENGTH_LONG).show();
                            create(database, activity, message, notifyResult).show();
                        }
                    }
                });
        return builder;
    }

    public static void update(DB.Database database, AccountController accountController, AccountData account) {
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(AccountKey.NOME, account.getNome());
        contentValues.put(AccountKey.ADMINISTRATOR, account.isAdministrator());
        database.update(AccountKey.NOME_TABELLA, contentValues, AccountKey.ID+"="+accountController.getRecord().getId());
    }


    public interface NotifyResultDelete {
        void notifyResult(boolean result);
    }
    /**result=-1 fail, result>-1=id*/
    public interface NotifyResultCreate {
        void notifyResult(long result);
    }
}
