package com.example.android.svapliquid.Activity.fragment.user;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Prodotti;
import com.example.android.svapliquid.Activity.Prodotto;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.adapter.ProdottiListView;
import com.example.android.svapliquid.Activity.fragment.MenuManager;
import com.example.android.svapliquid.Activity.fragment.NavigationFragment;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Andorid on 03/08/2017.
 */

public class CarelloF extends NavigationFragmentWithActionBar<MainActivity> {
    public final static String TAG = "CarelloF - ";
    ProdottiListView listAdapter;
    ListView listView;
    Prodotti prodotti;
    TextView textView;
    MenuManager menuManager;

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(ILog.LOG_TAG, TAG+ "createView: nf"+getFragmentManager().getBackStackEntryCount());
        View view = inflater.inflate(R.layout.fragment_carello, container, false);

        Log.i(ILog.LOG_TAG, TAG + "onCreateView");
        listView = (ListView) view.findViewById(R.id.listView_fragmentCarello);
        this.prodotti = this.activity.prodotti;
        listAdapter = new ProdottiListView(listView, prodotti, activity);
        listAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prodotti.size() != 0) {
                    activity.onFragmentOpenedToBackStack(CarelloFDescriptLiquid.getInstance(position));
                }
            }
        });
        listView.setAdapter(listAdapter);
        textView = (TextView) view.findViewById(R.id.textView_fragmentCarello_prezzo);
        textView.setText(prodotti.getPrezzo() + "€");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(ILog.LOG_TAG, TAG + "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(ILog.LOG_TAG, TAG + "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(ILog.LOG_TAG, TAG + "onResume");
    }

    public void refresh() {
        listAdapter.notifyDataSetChanged();
        textView.setText(prodotti.getPrezzo() + "€");
    }

    public static NavigationFragment getInstance() {
        CarelloF fragment = new CarelloF();
        return fragment;
    }



    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, ActionBar actionBar) {
        this.menuManager = new MenuManager(menu, inflater, new MenuManager.MenuLittleManager(R.menu.fragment_carello_menu_option,
                new MenuManager.OnOptionsItemSelectedListener() {
                    @Override
                    public boolean onOptionsItemSelected(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuOption1_fragmentCarello:
                                new AlertDialog.Builder(activity).setTitle("Vuoi scuotare il carello?").setMessage("Sei sicuro di volere eliminare completamente tutti i prodotti che ci sono nel carello?")
                                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                prodotti.clear();
                                                refresh();
                                            }
                                        }).setNeutralButton("ANNULLA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                                break;

                            case R.id.menuOption2_fragmentCarello:
                                if (activity.prodotti.size() == 0) {
                                    Toast.makeText(activity, "Carello Vuoto!", Toast.LENGTH_LONG).show();
                                } else {
                                    new AlertDialog.Builder(activity).setTitle("ORDINARE?").setMessage("Non modificare il testo in nessun modo, invialo e basta.")
                                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                                    whatsappIntent.setType("text/plain");
                                                    whatsappIntent.setPackage("com.whatsapp");
                                                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, activity.prodotti.getMessage(activity.account.getRecordUI().getNome()));
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
                                }
                                break;
                            case R.id.menuOption3_fragmentCarello:
                                new AlertDialog.Builder(activity).setTitle("INFORMAZIONI").setMessage("Se si acquistano 3 o più liquidi si ha uno sconto di 0,30€ per ogni liquido.\n" +
                                        "Se si acquistano 6 o piu liquidi si ha uno sconto del 0,50€ per ogni liquido.\n" +
                                        "Se si acquistano 10 o piu liquidi si ha uno sconto del 0,70€ per ogni liquido.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                                break;
                        }
                        return true;
                    }
                }));

        actionBar.setTitle("Carello");
        Log.i(ILog.LOG_TAG, TAG + "CreateOptionsMenu");
        listAdapter.setMenuLongClick(menuManager, new MenuManager.MenuLittleManager(R.menu.layout_list, new MenuManager.OnOptionsItemSelectedListener() {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_layoutList:
                        prodotti.removeAll(listAdapter.getItemsSelected());
                        refresh();
                }
                return true;
            }
        }));
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentCarello);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG + "OptionMenu Select");
        return menuManager.onOptionsItemSelected(item);
    }


    @Override
    public String getTagF() {
        return TAG;
    }

    @Override
    public boolean isSubHome() {
        return true;
    }
}
