package com.example.android.svapliquid.Activity.fragment.Containers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.svapliquid.Activity.ValoriRicerca;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 19/07/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    static final String TAG = "ExpandableListAdapter - ";

    private ArrayList<IdString> gruppo;
    private HashMap<Integer, ArrayList<IdString>> partecipanti = new HashMap<>();
    private Context _context;
    private int idTipoTiro;
    private ValoriRicerca valori;

    public ExpandableListAdapter(Context context, Gruppi gruppi, Partecipanti partecipanti) {
        //Log.i(ILog.LOG_TAG, TAG);
        this._context = context;
        this.gruppo = gruppi.getGruppo();
        //this.partecipanti = partecipanti.getPartecipanti();
    }
    public ExpandableListAdapter(Context context, int idTipoTiro, ValoriRicerca valori) {
        //Log.i(ILog.LOG_TAG, TAG);
        this._context = context;
        this.idTipoTiro = idTipoTiro;
        this.valori = valori;
        RisultatiRicerca rr = new RisultatiRicerca();
        rr.ricercaTutto(idTipoTiro, valori);
        gruppo = rr.getTableCategoria().getIdStrings();//rr.getTableCategoria()
    }

    @Override
    public String getChild(int groupPosition, int childPosititon) {
        return this.partecipanti.get(this.gruppo.get(groupPosition).getId()).get(childPosititon).getString();//this.rr.getLiquidoGroup(groupPosition, childPosititon).getNome();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        if (!this.partecipanti.containsKey(this.gruppo.get(groupPosition).getId())) {
            RisultatiRicerca ricerca = new RisultatiRicerca();
            ricerca.ricercaTutto(this.idTipoTiro, this.gruppo.get(groupPosition).getId(), valori);
            this.partecipanti.put(this.gruppo.get(groupPosition).getId(), ricerca.getLiquidi().getIdStrings());//ricerca.getLiquidi()
            //Log.i(ILog.LOG_TAG, TAG + "getChildrenCount"+ricerca.getLiquidi().getNumberRecords());
        }

        return this.partecipanti.get(this.gruppo.get(groupPosition).getId()).size();//this.rr.getNumeroLiquidiCategoria(groupPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.partecipanti.get(this.gruppo.get(groupPosition).getId()).get(childPosition).getId();
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Log.i(ILog.LOG_TAG, TAG + "getChilView");
        final String childText = getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_view_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.textViewExpandableListItem);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public String getGroup(int groupPosition) {
        //Log.i(ILog.LOG_TAG, TAG + this.gruppo.get(groupPosition).getString());
        return this.gruppo.get(groupPosition).getString();
    }
    @Override
    public int getGroupCount() {

        return this.gruppo.size();
    }
    @Override
    public long getGroupId(int groupPosition) {
        return this.gruppo.get(groupPosition).getId();
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //Log.i(ILog.LOG_TAG, TAG + "getGroupView");
        String headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_list_view_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.textViewExpandableListViewGroup);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
