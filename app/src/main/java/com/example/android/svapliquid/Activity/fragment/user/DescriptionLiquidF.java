package com.example.android.svapliquid.Activity.fragment.user;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 12/08/2017.
 */

public abstract class DescriptionLiquidF extends NavigationFragmentWithActionBar<MainActivity> {
    private static final String TAG = "DescriptionLiquidF";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        if (activity.isAdministrator()) inflater.inflate(R.menu.prodotto_menu_option_ad, menu);
        else inflater.inflate(R.menu.prodotto_menu_option, menu);
    }
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG + "OptionMenu Select");
        switch (item.getItemId()) {
            case R.id.menuOption1_prodotto:
                AlertDialog.Builder builderInformazioni = new AlertDialog.Builder(activity);
                builderInformazioni.setTitle("INFORMAZIONI");
                builderInformazioni.setMessage("VG/PG: COS'E'?\n" +
                        "Più Vg si decide più il liquido sarà denso e anche il fumo.\n" +
                        "Più PG si decide più il liquido sarà aromatico e molto meno denso.");
                builderInformazioni.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builderInformazioni.show();
                break;
        }
        return true;
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return  (Toolbar) view.findViewById(R.id.toolbar_fragmentLiquidDescription);
    }
}
