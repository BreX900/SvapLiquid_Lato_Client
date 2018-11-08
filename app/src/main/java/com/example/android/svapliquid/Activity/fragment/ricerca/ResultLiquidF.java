package com.example.android.svapliquid.Activity.fragment.ricerca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TipoTiri;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TipoTiro;
import com.example.android.svapliquid.Activity.fragment.Containers.Gruppo;
import com.example.android.svapliquid.Activity.fragment.Containers.IdString;
import com.example.android.svapliquid.Activity.fragment.IBackOn;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


/**
 * Created by Android on 18/07/2017.
 */

public class ResultLiquidF extends NavigationFragmentWithActionBar<MainActivity> implements IBackOn {
    public static final String TAG = "ResultLiquidF - ";
    public static final String RICERCA_VALORI = "ricercaValori";
    public SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    RisultatiRicerca rr;
    static int i=0;
    ValoriRicerca valoriRicerca;
    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(ILog.LOG_TAG, TAG+ "createView");
        View rootView = inflater.inflate(R.layout.fragment_liquid, container, false);
        //Log.i(ILog.LOG_TAG, TAG + "OnCreateView"+mainActivity.produttore);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(RICERCA_VALORI)) {
                valoriRicerca = (ValoriRicerca) bundle.getSerializable(RICERCA_VALORI);
                Log.i(ILog.LOG_TAG, TAG + "Creazione view: ii");
            } else {
                valoriRicerca = new ValoriRicerca();
                Log.i(ILog.LOG_TAG, TAG + "Creazione view: ie");
            }
        } else {
            valoriRicerca = new ValoriRicerca();
            Log.i(ILog.LOG_TAG, TAG + "Creazione view: e");
        }
        this.rr = new RisultatiRicerca();
        this.rr.ricercaTutto(valoriRicerca);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), this.rr.getTipoTiri(), valoriRicerca);
        // Set up the ViewPager with the sections adapter.
        this.mViewPager = rootView.findViewById(R.id.fragmentLiquid_ViewPager);
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);

        TabLayout tabLayout = rootView.findViewById(R.id.fragmentLiquid_Tab);
        tabLayout.setupWithViewPager(this.mViewPager);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        actionBar.setTitle("Risultato Ricerca");
        actionBar.setSubtitle("Tipologie di Tiro: ");
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentLiquid);
    }

    public static ResultLiquidF getInstance(ValoriRicerca valoriRicerca) {
        ResultLiquidF resultLiquidF = new ResultLiquidF();
        if (valoriRicerca.isEmpty()) return resultLiquidF;
        resultLiquidF.getArguments().putSerializable(RICERCA_VALORI, valoriRicerca);
        return resultLiquidF;
    }
    @Override
    public String getTagF() {
        return ResultLiquidF.TAG;
    }

    @Override
    public void onDestroyView() {
        Log.i(ILog.LOG_TAG, TAG+ "onDestroyView: "+getFragmentManager().getBackStackEntryCount());
        super.onDestroyView();
    }

    @Override
    public boolean isSubHome() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                new AlertDialog.Builder(activity).setTitle("Condividere Prodotti?").setMessage("Questa opzione permette di condividere i prodotti disponibili. Non modificare il testo in nessun modo, invialo e basta.")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                whatsappIntent.setType("text/plain");
                                whatsappIntent.setPackage("com.whatsapp");
                                whatsappIntent.putExtra(Intent.EXTRA_TEXT, getMessage());
                                try {
                                    activity.startActivity(whatsappIntent);
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(activity, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private String getMessage() {
        String message = "I liquidi sono divisi in 3 categorie per tipo di tiro: Guancia, Polmone e Any(Guancia/Polmone).\n";
        TipoTiri tipoTiri = this.rr.getTipoTiri();
        for (int i=0; i<tipoTiri.getNumberRecords(); i++) {
            TipoTiro tipoTiro = tipoTiri.getRecordByPosition(i);
            RisultatiRicerca rr = new RisultatiRicerca();
            rr.ricercaTutto(tipoTiro.getId(), this.valoriRicerca);
            ArrayList<IdString> gruppi = rr.getTableCategoria().getIdStrings();//rr.getTableCategoria()
            message += "---------- *"+tipoTiro.getNome()+"* ----------\n";
            for (int g=0; g<gruppi.size(); g++) {
                IdString gruppo = gruppi.get(g);
                message += "----- _"+gruppo.getString()+"_ -----\n";
                RisultatiRicerca rr2 = new RisultatiRicerca();
                rr2.ricercaTutto(tipoTiro.getId(), gruppo.getId(), this.valoriRicerca);
                ArrayList<IdString> liquidi = rr2.getLiquidi().getIdStrings();//rr2.getLiquidi()
                for (int l=0; l<liquidi.size(); l++) {
                   message += "- "+liquidi.get(l).getString()+"\n";
                }
            }
        }
        return message;
    }
}