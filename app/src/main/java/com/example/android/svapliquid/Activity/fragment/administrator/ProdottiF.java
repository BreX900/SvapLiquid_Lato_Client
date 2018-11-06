package com.example.android.svapliquid.Activity.fragment.administrator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.Adapter.RecordUIAdapter;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.ProdottoRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.NotifyDataSetChanged;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.controller.prodotto.ProdottiControllers;
import com.example.android.svapliquid.Activity.Ordin.controller.prodotto.ProdottoController;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.index.ProdottoKey;
import com.example.android.svapliquid.Activity.Ordin.record.ProdottoRecord;
import com.example.android.svapliquid.Activity.TextViewPrezzo;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;
import com.example.android.svapliquid.Activity.fragment.MenuManager;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 09/11/2017.
 */

public class ProdottiF  extends NavigationFragmentWithActionBar<MainActivity> implements MenuManager.OnOptionsItemSelectedListener{
    public static final String TAG = "ProdottiF - ";
    private static final String ID_ORDINE = "idOrdine";

    private MenuManager menuManager;
    private ProdottiUIAdapter prodottiAdapter;

    private ProdottiControllers prodottoRecords;

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_prodotti, container, false);
        Log.i(ILog.LOG_TAG, TAG + "OnCreateView: ");

        Bundle args = getArguments();
        if (args != null)
            if (args.containsKey(ID_ORDINE)) {
                long idOrdine = args.getLong(ID_ORDINE);
                prodottoRecords = new ProdottiControllers(DB.getAccountDatabase(), new QueryString().addSelectAll().addFrom(ProdottoKey.NOME_TABELLA).addWhereAnd(ProdottoKey.ID_ORDINE+"="+idOrdine));


            }

        ListView listView = (ListView) view.findViewById(R.id.listView_fragmentProdotti);
        prodottiAdapter = new ProdottiUIAdapter(listView, prodottoRecords, activity);
        RecordControllers.ControllerGuiController.addGui(ProdottoKey.NOME_TABELLA, prodottiAdapter);
        listView.setAdapter(prodottiAdapter);

        RecordControllers.ControllerGuiController.addGui(ProdottoKey.NOME_TABELLA, new NotifyDataSetChanged() {
            @Override
            public void notifyDataSetChanged() {
                checkList();
            }
        });

        return view;
    }
    private void checkList() {
        if (prodottoRecords.isEmpty()) {
            activity.onBackPressed();
            Toast.makeText(activity, "NESSUN PRODOTTO TROVATO!", Toast.LENGTH_LONG).show();
        }
    }

    public static ProdottiF getInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ID_ORDINE, id);
        ProdottiF prodottiF = new ProdottiF();
        prodottiF.setArguments(args);
        return prodottiF;
    }

    @Override
    public String getTagF() {
        return TAG;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, ActionBar actionBar) {
        this.menuManager = new MenuManager(menu, inflater, new MenuManager.MenuLittleManager(R.menu.fragment_prodotti_menu_option, new MenuManager.OnOptionsItemSelectedListener() {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.showAll:
                        prodottoRecords.setQueryString(QueryString.getDefaultQuery(ProdottoKey.NOME_TABELLA));
                        RecordControllers.ControllerGuiController.refresh(ProdottoKey.NOME_TABELLA);
                        break;
                }
                return true;
            }
        }));
        actionBar.setTitle("Prodotti");
        actionBar.setSubtitle("----");
        Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu");
        prodottiAdapter.setMenuLongClick(menuManager, new MenuManager.MenuLittleManager(R.menu.layout_list, new MenuManager.OnOptionsItemSelectedListener() {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu: "+item.getItemId());
                switch (item.getItemId()) {
                    case R.id.delete_layoutList:
                        Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu: delete");
                        prodottoRecords.delete(prodottiAdapter.getItemsSelected());
                        RecordControllers.ControllerGuiController.refresh(ProdottoKey.NOME_TABELLA);
                }
                return true;
            }
        }));
        actionBar.setSubtitle("Ordini");
        Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu");
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentProdotti);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG +"OptionMenu Select");
        return this.menuManager.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        RecordControllers.ControllerGuiController.destroy(ProdottoKey.NOME_TABELLA);
        super.onDestroyView();
    }

    class ProdottiUIAdapter extends RecordUIAdapter<ProdottoRecord, ProdottoRecordUI, ProdottoController> {
        private final Context context;

        ProdottiUIAdapter(final ListView listView, final ProdottiControllers prodottiControllers, final Context context) {
            super(listView, prodottiControllers);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent, boolean selected) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_view_item_prodotto, null);
                viewHolder = new ViewHolder();
                viewHolder.textViewComposizione = (TextView) convertView.findViewById(R.id.textView_itemProdotto_composizione);
                viewHolder.textViewNome = (TextView) convertView.findViewById(R.id.textView_itemProdotto_nome);
                viewHolder.textViewNicotina = (TextView) convertView.findViewById(R.id.textView_itemProdotto_nicotina);
                viewHolder.textViewBocetta = (TextView) convertView.findViewById(R.id.textView_itemProdotto_boccetta);
                viewHolder.textPrezzo = new TextViewPrezzo(convertView.findViewById(R.id.textView_listViewItemProdotto_Prezzo));
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (isEmpty()) {
                viewHolder.textViewComposizione.setText(" ");
                viewHolder.textViewNome.setText("IL CARELLLO E' VUOTO");
                viewHolder.textViewNicotina.setText(" ");
                viewHolder.textViewBocetta.setText(" ");
                viewHolder.textPrezzo.setText(" ");
            }
            else {
                ProdottoRecordUI prodotto = getItemRecordUI(position);
                viewHolder.textViewComposizione.setText(prodotto.getComposizione());
                viewHolder.textViewNome.setText(prodotto.getNome());
                viewHolder.textViewNicotina.setText(prodotto.getNicotina() + "");
                viewHolder.textViewBocetta.setText(prodotto.getBoccetta()+ " - " + prodotto.getMl());
                viewHolder.textPrezzo.setText(prodotto.getPrezzoData().getPrezzo());
            }
            return convertView;
        }


        private class ViewHolder {
            TextView textViewComposizione;
            TextView textViewNome;
            TextView textViewNicotina;
            TextView textViewBocetta;
            TextViewPrezzo textPrezzo;
        }
    }
}
