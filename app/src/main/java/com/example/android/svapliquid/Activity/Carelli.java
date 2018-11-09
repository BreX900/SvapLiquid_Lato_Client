package com.example.android.svapliquid.Activity;

import android.util.Log;

import com.example.android.svapliquid.Activity.Ordin.controller.account.AccountsController;
import com.example.android.svapliquid.Activity.databases.DB;

import java.util.ArrayList;

public class Carelli extends ArrayList<Carello> {
    private static final String TAG = "Carelli.";
    public Carelli() {
        AccountsController accountsController = new AccountsController(DB.getAccountDatabase());
        for (int i=0; i<accountsController.size(); i++) {
            try {
                Log.i(ILog.LOG_TAG, TAG+"(): notadd");
                this.add(new Carello(accountsController.get(i).getRecord().getNome()));
                Log.i(ILog.LOG_TAG, TAG+"(): add");
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }


}
