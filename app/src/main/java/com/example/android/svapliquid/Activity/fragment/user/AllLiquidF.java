package com.example.android.svapliquid.Activity.fragment.user;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.fragment.ricerca.ResultLiquidF;
import com.example.android.svapliquid.R;

/**
 * Created by Andorid on 08/08/2017.
 */

public class AllLiquidF extends ResultLiquidF {
    public static final String TAG = "AllLiquidF - ";

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        inflater.inflate(R.menu.fragment_all_liquid, menu);
        Log.i(ILog.LOG_TAG, TAG+ "onCreateOptionsMenu");
        actionBar.setTitle("Tutti i Liquidi");
    }



    @Override
    public boolean isHome() {
        return true;
    }

    @Override
    public String getTagF() {
        return TAG;
    }
    public static AllLiquidF getInstance() {
        return new AllLiquidF();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                new AlertDialog.Builder(activity).setTitle("INFO").setMessage("✔️ = Liquido Disponibile\n❌ = Liquido Non Disponibile").setNeutralButton("OK",null).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
