package com.example.android.svapliquid.Activity.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.Adapter.ListSelected;
import com.example.android.svapliquid.Activity.Prodotti;
import com.example.android.svapliquid.Activity.Prodotto;
import com.example.android.svapliquid.Activity.TextViewPrezzo;
import com.example.android.svapliquid.R;

/**
 * Created by Andorid on 05/08/2017.
 */

public class ProdottiListView extends ListSelected<Prodotto, Prodotti> {
    public static final String TAG = "ProdottiListView - ";
    final Prodotti prodotti;
    final AppCompatActivity activity;
    boolean isEmpty = false;
    public ProdottiListView(ListView listView, Prodotti prodotti, AppCompatActivity activity) {
        super(listView, prodotti);
        this.prodotti = prodotti;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        int count;
        if ((count = prodotti.size()) == 0) {
            isEmpty = true;
            count++;
        }
        return count;
    }

    @Override
    public Prodotto getItem(int position) {
        return this.prodotti.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, boolean selected) {
        Log.i(ILog.LOG_TAG, TAG +"getView: "+selected+" po:"+position);
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (isEmpty) {
            viewHolder.textViewComposizione.setText(" ");
            viewHolder.textViewNome.setText("IL CARELLLO E' VUOTO");
            viewHolder.textViewNicotina.setText(" ");
            viewHolder.textViewBocetta.setText(" ");
            viewHolder.textPrezzo.setText(" ");
        }
        else {
            Prodotto prodotto = getItem(position);
            viewHolder.textViewComposizione.setText(prodotto.getComposizione());
            viewHolder.textViewNome.setText(prodotto.getLiquido().getNome());
            viewHolder.textViewNicotina.setText(prodotto.getMgNicotina() + "");
            viewHolder.textViewBocetta.setText(prodotto.getBoccetta().getNome() + " - " + prodotto.getBoccetta().getMl());
            viewHolder.textPrezzo.setText(prodotto.getPrezzo());
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
