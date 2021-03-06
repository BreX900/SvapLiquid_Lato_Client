package com.example.android.svapliquid.Activity.fragment.administrator;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.svapliquid.Activity.Carelli;
import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.OrdineDataAdapter;
import com.example.android.svapliquid.Activity.Ordin.UI.Adapter.RecordUIAdapter;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.OrdineRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.controller.ordine.OrdineController;
import com.example.android.svapliquid.Activity.Ordin.controller.ordine.OrdiniController;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineGUI;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.OrdineRecord;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.fragment.MenuManager;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Andorid on 04/08/2017.
 */

public class OrdiniF extends NavigationFragmentWithActionBar<MainActivity> {
    public static final String TAG = "OrdiniF - ";
    OrdiniUIAdapter ordiniAdapter;
    private OrdiniController ordiniController;
    private MenuManager menuManager;
    QueryString defaultQuery;

    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ordini, container, false);
        Log.i(ILog.LOG_TAG, TAG + "OnCreateView: ");
        //Questa stringa era in onCreate
        this.defaultQuery = new QueryString().addSelect(OrdineKey.NOME_TABELLA + ".*").addSelect("SUM(" + ProdottoKey.PREZZO + ") AS " + OrdineController.KEY_PREZZO_CALCOLATO)
                .addFrom(OrdineKey.NOME_TABELLA).addFrom(ProdottoKey.NOME_TABELLA)
                .addWhereAnd(OrdineKey.ID_ACCOUNT + "=" + activity.account.getRecord().getId()).addWhereAnd(OrdineKey.NOME_TABELLA + "." + OrdineKey.ID + "=" + ProdottoKey.NOME_TABELLA + "." + ProdottoKey.ID_ORDINE)
                .addGroup(OrdineKey.NOME_TABELLA + "." + ProdottoKey.ID_ORDINE).addOrderDesc(OrdineKey.DATA).addOrderDesc(OrdineKey.ID);
        this.ordiniController = new OrdiniController(DB.getAccountDatabase(), defaultQuery);
        ListView listView = rootView.findViewById(R.id.listView_fragmentOrdini);
        ordiniAdapter = new OrdiniUIAdapter(listView, ordiniController, activity);
        RecordControllers.ControllerGuiController.addGui(OrdineKey.NOME_TABELLA, ordiniAdapter);
        ordiniAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity.onFragmentOpenedToBackStack(ProdottiF.getInstance(ordiniAdapter.getItemId(position)));
            }
        });
        listView.setAdapter(ordiniAdapter);
        return rootView;
    }

    public static OrdiniF getInstance() {
        return new OrdiniF();
    }

    @Override
    public String getTagF() {
        return TAG;
    }

    @Override
    public boolean isSubHome() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, final ActionBar actionBar) {
        this.menuManager = new MenuManager(menu, inflater, new MenuManager.MenuLittleManager(R.menu.fragment_ordini_menu_option, new MenuManager.OnOptionsItemSelectedListener() {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuOption1_fragmentOrdine:
                        OrdineDataAdapter.dialogAddOrdine(activity, activity.account.getRecord().getId(), new Carelli(), ordiniController);
                        break;

                    case R.id.showAll:
                        ordiniController.setQueryString(defaultQuery);
                        RecordControllers.ControllerGuiController.refresh(OrdineKey.NOME_TABELLA);
                        break;
                }
                return true;
            }
        }));
        actionBar.setSubtitle("Ordini");
        Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu");
        ordiniAdapter.setMenuLongClick(menuManager, new MenuManager.MenuLittleManager(R.menu.layout_list, new MenuManager.OnOptionsItemSelectedListener() {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_layoutList:
                        final ArrayList<OrdineController> ordini = ordiniAdapter.getItemsSelected();
                        new AlertDialog.Builder(activity).setTitle("Sei sicuro di voler CANCELLARE per sempre "+ordini.size()+" ordini?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ordiniController.delete(ordini);
                                RecordControllers.ControllerGuiController.refresh(OrdineKey.NOME_TABELLA);
                            }
                        }).setNegativeButton("NO", null).show();
                        break;
                    case R.id.modify:
                        ordiniController.update(ordiniAdapter.getItemsSelected(), activity, activity.account.getRecord().getId());
                       break;
                }
                return true;
            }
        }));
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentOrdini);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG +"OptionMenu Select");
        return this.menuManager.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        RecordControllers.ControllerGuiController.destroy(OrdineKey.NOME_TABELLA);
        super.onDestroyView();
    }

    class OrdiniUIAdapter extends RecordUIAdapter<OrdineRecord, OrdineRecordUI, OrdineController> {
        private final Context context;

        OrdiniUIAdapter(final ListView listView, final OrdiniController ordiniController, final Context context) {
            super(listView, ordiniController);
            this.context = context;
        }
        OrdineGUI ordineGUI;
        @Override
        public View getView(int position, View convertView, ViewGroup parent, boolean selected) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_view_item_ordine, null);
                viewHolder = new ViewHolder();
                viewHolder.textViewIdOrdine = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_idOrdine);
                viewHolder.textViewNomeOrdine = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_nomeOrdine);
                viewHolder.textViewPrezzoCalcolato = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_prezzoCalcolato);
                viewHolder.textViewStato = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_stato);
                viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_date);
                viewHolder.textViewPrezzoStimato = (TextView) convertView.findViewById(R.id.textView_ListViewItemOrdine_prezzoStimato);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (isEmpty()) {
                viewHolder.textViewIdOrdine.setText("-");
                viewHolder.textViewNomeOrdine.setText("IL CARELLLO E' VUOTO");
                viewHolder.textViewPrezzoCalcolato.setText("0.00");
                viewHolder.textViewStato.setText(" ");
                viewHolder.textViewDate.setText(" ");
                viewHolder.textViewPrezzoStimato.setText("0.00");
            } else {
                OrdineRecordUI ordineRecordUI = getItemRecordUI(position);
                ordineGUI = new OrdineGUI(new OrdineData(ordineRecordUI.getItem()), ordineRecordUI.getPrezzoCalcolato());
                viewHolder.textViewIdOrdine.setText(position+"");
                viewHolder.textViewNomeOrdine.setText(ordineGUI.getNome());
                viewHolder.textViewPrezzoCalcolato.setText(ordineGUI.getPrezzo());
                viewHolder.textViewStato.setText(ordineGUI.getStato());
                viewHolder.textViewDate.setText(ordineGUI.getDate());
                viewHolder.textViewPrezzoStimato.setText(ordineGUI.getPrezzoCalcolato());
            }
            return convertView;
        }

        class ViewHolder {
            TextView textViewIdOrdine;
            TextView textViewNomeOrdine;
            TextView textViewPrezzoCalcolato;
            TextView textViewStato;
            TextView textViewDate;
            TextView textViewPrezzoStimato;
        }

        @Override
        public int colorAlternativ(boolean selected, int position) {
            if (ordineGUI.getWarning())
                if (selected) return 0xffcc8500;//arancione scuro
                else return 0xffffbc40; //arancione chiaro
            else return super.colorAlternativ(selected, position);
        }
    }
}
