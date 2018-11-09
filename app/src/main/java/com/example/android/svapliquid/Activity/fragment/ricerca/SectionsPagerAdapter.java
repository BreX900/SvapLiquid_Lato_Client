package com.example.android.svapliquid.Activity.fragment.ricerca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.tipoTiro.TipoTiri;
import com.example.android.svapliquid.Activity.fragment.Containers.ExpandableListAdapter;
import com.example.android.svapliquid.Activity.fragment.NavigationFragment;
import com.example.android.svapliquid.Activity.fragment.user.LiquidDescriptionF;
import com.example.android.svapliquid.R;

import java.util.HashMap;

/**
 * Created by Android on 21/07/2017.
 */

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {
    final static String TAG = "SectionsPagerAdapter";
    TipoTiri tipoTiri;
    ValoriRicerca valori;
    /**Fragment Manager is Child of main fragment*/
    public SectionsPagerAdapter(FragmentManager fragmentManager, TipoTiri tipoTiri, ValoriRicerca valori) {
        super(fragmentManager);
        this.tipoTiri = tipoTiri;
        this.valori = valori;
    }
    @Override
    public Fragment getItem(int position) {
        if (tipoTiri.isEmpty())
            return new Fragment();
        return PlaceholderFragment.newInstance(this.tipoTiri.getRecordByPosition(position).getId(), valori);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (tipoTiri.isEmpty())
            return "NESSUN RISULTATO!";
        return tipoTiri.getRecordByPosition(position).getNome();
    }
    @Override
    public int getCount() {
        if (tipoTiri.isEmpty())
            return 1;
        return tipoTiri.getNumberRecords();
    }


    /**A placeholder fragment containing a simple view.*/
    public static class PlaceholderFragment extends NavigationFragment<MainActivity> {
        final static String TAG = "PlaceholderFragment";
        ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        /**The fragment argument representing the section number for this fragment.*/
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_VALUE = "value";

        public PlaceholderFragment () {}

        @Override
        public String getTagF() {
            return null;
        }
        //int idTipoTiro = this.getArguments().getInt(ARG_SECTION_NUMBER);

        /**Returns a new instance of this fragment for the given sectionnumber.*/
        static PlaceholderFragment newInstance(int idPage, HashMap<String, String> valori) {
            Log.i(ILog.LOG_TAG, "SectionsPagerAdapter.PlaceholderFragment" + "PlaceHolderFragment newInstance: "+idPage);
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle bundle = fragment.getArguments();
            bundle.putInt(ARG_SECTION_NUMBER, idPage);
            bundle.putSerializable(ARG_VALUE, valori);
            //fragment.setArguments(args);
            return fragment;
        }
        View rootView;
        int idTipoTiro = -1;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            this.idTipoTiro = this.getArguments().getInt(ARG_SECTION_NUMBER);
            rootView = inflater.inflate(R.layout.fragment_liquid_page, container, false);
            ValoriRicerca valori = (ValoriRicerca) this.getArguments().getSerializable(ARG_VALUE);
            Log.i(ILog.LOG_TAG, "SectionsPagerAdapter.PlaceholderFragment" + "onCreateView: " + this.idTipoTiro);
            this.expandableListView = (ExpandableListView) rootView.findViewById(R.id.fragmentLiquidPage_ExpandableListView);
            this.expandableListAdapter = new ExpandableListAdapter(this.getContext(), this.idTipoTiro, valori);
            this.expandableListView.setAdapter(this.expandableListAdapter);
            this.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    Log.i(ILog.LOG_TAG, TAG + "onChildClick");
                    int idLiquido = (int) expandableListAdapter.getChildId(groupPosition, childPosition);
                    activity.onFragmentOpenedToBackStack(LiquidDescriptionF.getInstance(idLiquido));
                    return true;
                }
            });
            for (int i = 0; i < this.expandableListAdapter.getGroupCount(); i++) {
                this.expandableListView.expandGroup(i);
            }
            return rootView;
        }
    }


    /*ArrayList<Fragment> listFragment = new ArrayList<>();
    //static final String TAG = "SectionsPagerAdapter - ";
    int i=0;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.i(ILog.LOG_TAG, "SectionsPagerAdapter - " + "SectionsPagerAdapter: Create");
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //if (position == 0) position++;
        Log.i(ILog.LOG_TAG, "SectionsPagerAdapter - " + "getItem: "+position);
        return  PlaceholderFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {// Show 3 total pages.
        //Log.i(ILog.LOG_TAG, "SectionsPagerAdapter - " + "getCount: ");
        return 3;//rr.getNumberTipoTiro();
    }

    @Override
    public long getItemId(int position) {
        Log.i(ILog.LOG_TAG, "SectionsPagerAdapter - " + "getItemId: "+position);
        return position;
    }*/
}

