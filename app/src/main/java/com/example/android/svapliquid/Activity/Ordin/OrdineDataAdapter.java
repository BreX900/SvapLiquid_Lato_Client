package com.example.android.svapliquid.Activity.Ordin;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.Carelli;
import com.example.android.svapliquid.Activity.Carello;
import com.example.android.svapliquid.Activity.Ordin.controller.DateController;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.controller.ordine.OrdiniController;
import com.example.android.svapliquid.Activity.Ordin.controller.prodotto.ProdottiControllers;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.data.PrezzoData;
import com.example.android.svapliquid.Activity.Ordin.data.ProdottiData;
import com.example.android.svapliquid.Activity.Ordin.data.ProdottoData;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Prodotti;
import com.example.android.svapliquid.Activity.Prodotto;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

/**
 * Created by Andorid on 10/11/2017.
 */

public class OrdineDataAdapter {
    public static void dialogAddOrdine(final AppCompatActivity activity, final int idAccount, final Carelli carelli, final OrdiniController ordiniController) {
        final View view = activity.getLayoutInflater().inflate(R.layout.alert_dialog_add_ordini, null);
        ListView listView = view.findViewById(R.id.listView_addOrdini);
        final CarelliAdapter carelliAdapter = new CarelliAdapter(carelli, activity);
        listView.setAdapter(carelliAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Carello carello = carelli.get(position);
                dialogAddOrdine(activity, idAccount, new OrdineData(carello.getUser(), carello.getProdotti().getPrezzo()), carello.getProdotti(), ordiniController);
            }
        });
        new AlertDialog.Builder(activity).setTitle("Seleziona l'utente che ordina:").setView(view)
                .setNeutralButton("OK", null).show();

    }

    public static void dialogAddOrdine(final AppCompatActivity activity, final int idAccount, final Prodotti prodotti, final OrdiniController ordiniController) {
        dialogAddOrdine(activity, idAccount, new OrdineData(prodotti.getPrezzo()), prodotti, ordiniController);
    }

    public static void dialogAddOrdine(final AppCompatActivity activity, final int idAccount, final OrdineData ordineData, final Prodotti prodotti, final OrdiniController ordiniController) {
        if (prodotti.isEmpty()) {
            Toast.makeText(activity, "Carello vuoto", Toast.LENGTH_LONG).show();
        } else {
            final LayoutInflater inflater = activity.getLayoutInflater();
            final View view = inflater.inflate(R.layout.alert_dialog_add_ordine, null);
            final EditText editText_nome = (EditText) view.findViewById(R.id.editText_alertDialogAddOrdine_nomeOrdine);
            editText_nome.setText(ordineData.getNome());
            final EditText editText_prezzo = (EditText) view.findViewById(R.id.editText_alertDialogAddOrdine_prezzo);
            editText_prezzo.setText(ordineData.getPrezzo().getPrezzo() + "");
            final EditText editText_stato = (EditText) view.findViewById(R.id.editText_alertDialogAddOrdine_stato);
            editText_stato.setText(ordineData.getStato());
            final EditText editText_informazioni = (EditText) view.findViewById(R.id.editText_alertDialogAddOrdine_informazioni);
            editText_informazioni.setText(ordineData.getInformazioni());
            final EditText editText_date = (EditText) view.findViewById(R.id.editText_alertDialogAddOrdine_date);
            if (ordineData.getDate().isSet()) {
                editText_date.setText(ordineData.getDate().getDateIt());
            }
            editText_date.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DateController.getDateDialog(activity, ordineData.getDate(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editText_date.setText(ordineData.getDate().getDateIt());
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(editText_date.getWindowToken(), 0);
                        }
                    }, null).show();
                    return true;
                }
            });

            new AlertDialog.Builder(activity).setTitle("Aggiungi all'archivio").setView(view).setPositiveButton("AGGIUNGI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    ordineData.setNome(editText_nome.getText().toString());
                    ordineData.getPrezzo().setPrezzo(Double.parseDouble(editText_prezzo.getText().toString()));
                    ordineData.setStato(editText_stato.getText().toString());
                    ordineData.setInformazioni(editText_informazioni.getText().toString());
                    try {
                        String stringDate = editText_date.getText().toString();
                        if (!stringDate.isEmpty()) {
                            ordineData.getDate().setDateIt(stringDate);
                        }
                        final int idOrdine = ordiniController.create(ordineData, idAccount);
                        ProdottiControllers.create(DB.getAccountDatabase(), getProdottiData(prodotti), idOrdine);
                        RecordControllers.ControllerGuiController.refresh(OrdineKey.NOME_TABELLA);
                    } catch (ParseException exc) {
                        dialogAddOrdine(activity, idAccount, ordineData, prodotti, ordiniController);
                    }
                }
            }).setNeutralButton("ANNULLA", null).setCancelable(false).show();
        }
    }

    public static ProdottiData getProdottiData(@NotNull final Prodotti prodotti) {
        final ProdottiData prodottiData = new ProdottiData(prodotti.size());
        for (int i = 0; i < prodotti.size(); i++) {
            prodottiData.add(getProdottoData(prodotti.get(i)));
        }
        return prodottiData;
    }

    public static ProdottoData getProdottoData(@NotNull final Prodotto prodotto) {
        return new ProdottoData(prodotto.getLiquido().getNome(), prodotto.getComposizione(), prodotto.getMgNicotina(), (int) prodotto.getBoccetta().getMl(),
                prodotto.getBoccetta().getNome(), new PrezzoData(prodotto.getPrezzo()));
    }

    static class CarelliAdapter extends BaseAdapter {
        private final Carelli carelli;
        private final AppCompatActivity activity;

        CarelliAdapter(final Carelli carelli, final AppCompatActivity activity) {
            this.carelli = carelli;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return this.carelli.size();
        }

        @Override
        public Carello getItem(int position) {
            return this.carelli.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CarelliAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(android.R.layout.simple_list_item_1, null);
                viewHolder = new CarelliAdapter.ViewHolder();
                viewHolder.textViewUser = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (CarelliAdapter.ViewHolder) convertView.getTag();
            }
            if (isEmpty()) {
                viewHolder.textViewUser.setText("Nessun Carello");
            } else {
                Carello carello = this.getItem(position);
                viewHolder.textViewUser.setText(carello.getUser());
            }
            return convertView;
        }

        private class ViewHolder {
            TextView textViewUser;
        }
    }
}
