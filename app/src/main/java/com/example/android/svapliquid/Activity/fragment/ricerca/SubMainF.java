package com.example.android.svapliquid.Activity.fragment.ricerca;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria.TableCategoria;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.linea.TableLinea;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquido;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.produttore.TableProduttore;
import com.example.android.svapliquid.Activity.fragment.IBackOn;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Android on 11/07/2017.
 */

public class SubMainF extends NavigationFragmentWithActionBar<MainActivity> implements IBackOn {

    public static final String TAG = "SubMainF - ";

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ricerca, container, false);
        Log.i(ILog.LOG_TAG, TAG + "OnCreateView");

        final Button button = (Button) view.findViewById(R.id.searchProduttore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ILog.LOG_TAG, TAG + "Search");
                ValoriRicerca valoriRicerca = new ValoriRicerca();
                valoriRicerca.put(TableCategoria.KEY_NOME, ((EditText) view.findViewById(R.id.editTextRicercaCategoria)).getText().toString());
                valoriRicerca.put(TableProduttore.KEY_NOME, ((EditText) view.findViewById(R.id.editTextRicercaProduttore)).getText().toString());
                valoriRicerca.put(TableLinea.KEY_NOME, ((EditText) view.findViewById(R.id.editTextRicercaLinea)).getText().toString());
                valoriRicerca.put(Liquido.KEY_NOME, ((EditText) view.findViewById(R.id.editTextSearchLiquido)).getText().toString());

                activity.onFragmentOpenedToBackStack(ResultLiquidF.getInstance(valoriRicerca));
                Log.i(ILog.LOG_TAG, TAG +"Open Fragment: "+ ResultLiquidF.TAG);
            }
        });

        final Button resetButton = (Button) view.findViewById(R.id.button_fragmentSubMain_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String any = "Any";
                ((EditText) view.findViewById(R.id.editTextRicercaCategoria)).setText(null);
                ((EditText) view.findViewById(R.id.editTextRicercaProduttore)).setText(null);
                ((EditText) view.findViewById(R.id.editTextRicercaLinea)).setText(null);
                ((EditText) view.findViewById(R.id.editTextSearchLiquido)).setText(null);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setTitle("Ricerca");
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentSubMain);
    }

    public static SubMainF getInstance() {
        return new SubMainF();
    }

    @Override
    public String getTagF() {
        return TAG;
    }

    @Override
    public boolean isHome() {
        return true;
    }
}
