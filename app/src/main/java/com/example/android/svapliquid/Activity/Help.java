package com.example.android.svapliquid.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.android.svapliquid.Activity.Ordin.controller.account.AccountController;
import com.example.android.svapliquid.Activity.Ordin.data.AccountData;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.R;

/**
 * Created by Andorid on 03/09/2017.
 */

public class Help {
    private static final String PASSWORD = "Start.0";
    public static void showHelp(final AppCompatActivity activity, final AccountController accountController) {
        new AlertDialog.Builder(activity).setPositiveButton("OK", null).setTitle("INFORMAZIONI").setMessage("I prodotti che vengono offerti sono di alta qualità, ad esempio le basi sono con certificazione farmaceutica,\n" +
                "In caso si tolga il tappo della boccetta o perchè si è premuto troppo o perchè il tappo aveva il buco un po' chiuso non siamo responsabili dei danni causati o della pardita di liquido in alun modo.\n" +
                "BUONA GIORANATA\n"+"APP. Versione: "+activity.getString(R.string.app_versione)).setNeutralButton("Admin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.alert_dialog_panel_administrator, null);
                final EditText editText_password = (EditText) view.findViewById(R.id.editText_alertDialogPanelAdministrator_password);
                new AlertDialog.Builder(activity).setTitle("AREA AMMINISTRATORE").setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText_password.getText().toString().isEmpty()) {
                            if (editText_password.getText().toString().equals(PASSWORD)) {
                                Log.i(ILog.LOG_TAG, editText_password.getText().toString());
                                AccountController.update(DB.getAccountDatabase(), accountController, new AccountData(accountController.getRecord().getNome(), true));
                            }
                        }
                    }
                }).setNeutralButton("ESCI", null).setNegativeButton("Restore", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AccountController.update(DB.getAccountDatabase(), accountController, new AccountData(accountController.getRecord().getNome(), false));
                    }
                }).show();
            }
        }).show();
    }
}
