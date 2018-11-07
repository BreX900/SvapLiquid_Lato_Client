package com.example.android.svapliquid.Activity.fragment.ricerca;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.Activity.fragment.IBackOn;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;


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
    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(ILog.LOG_TAG, TAG+ "createView");
        View rootView = inflater.inflate(R.layout.fragment_liquid, container, false);
        //Log.i(ILog.LOG_TAG, TAG + "OnCreateView"+mainActivity.produttore);
        Bundle bundle = getArguments();
        ValoriRicerca valoriRicerca;
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
}