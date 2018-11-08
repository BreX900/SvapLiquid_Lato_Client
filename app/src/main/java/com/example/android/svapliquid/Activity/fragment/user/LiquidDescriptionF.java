package com.example.android.svapliquid.Activity.fragment.user;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.OnActionListener;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Prodotto;
import com.example.android.svapliquid.Activity.TextViewPrezzo;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BaseR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BasiT;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquido;
import com.example.android.svapliquid.Activity.fragment.Containers.IdString;
import com.example.android.svapliquid.Activity.fragment.Containers.SpinBaseAdapter;
import com.example.android.svapliquid.R;

/**
 * Created by Android on 24/07/2017.
 */

public class LiquidDescriptionF extends DescriptionLiquidF {
    public final static String TAG = "LiquidDescriptionF - ";
    public final static String ID_LIQUIDO = "idLiquido";
    public final static String PRODOTTO = "prodotto";
    Prodotto prodotto;
    EditText editText;
    TextWatcher textWatcher;
    Liquido liquido;

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liquid_description, container, false);


        Log.i(ILog.LOG_TAG, TAG +"CreateLayout");
        Bundle bundle = this.getArguments();
        final RisultatiRicerca rr = new RisultatiRicerca();
        rr.getAllTableByIdLiquid(bundle.getInt(ID_LIQUIDO));
        BaseR baseR = rr.getBasi().getRecordByTipoBase(BasiT.TIPO_BASE_VG);
        BaseR baseNicotinata = rr.getBasi().getBasiConNicotina().getRecordByPosition(0);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_liquidDescription_idBoccetta);
        spinner.setAdapter(new SpinBaseAdapter(activity, rr.getBoccette().getIdStrings()));//rr.getBoccette()

        final Spinner spinnerComposizione = (Spinner) view.findViewById(R.id.spinner_liquidDescription_composizione);
        spinnerComposizione.setSaveEnabled(false);
        ArrayAdapter<CharSequence> adapterSpinnerComposizione = ArrayAdapter.createFromResource(activity, R.array.composizione_array, android.R.layout.simple_spinner_item);
        adapterSpinnerComposizione.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComposizione.setAdapter(adapterSpinnerComposizione);

        final ImageView imageView_helpBoccetta = (ImageView) view.findViewById(R.id.imageView_fragmentLiquidDescription_helpBoccetta);
        imageView_helpBoccetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                BoccettaR boccetta = BoccettaR.getRecordById(((IdString) spinner.getSelectedItem()).id);
                builder.setTitle("Informazioni "+boccetta.getIdentificativo()+":").setMessage(boccetta.getDescrizione());
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).show();
            }
        });

        prodotto = new Prodotto(new Liquido(bundle.getInt(ID_LIQUIDO)), 0, BoccettaR.getRecordById(((IdString) spinner.getSelectedItem()).id), baseR, baseNicotinata, rr.getRicette().getRecordByPosition(0));
        if (prodotto.getComposizione().equalsIgnoreCase("Any")) {
            prodotto.setComposizione("65/35");
        }
        else {
            spinnerComposizione.setEnabled(false);
        }
        String[] strings = getResources().getStringArray(R.array.composizione_array);
        for (int i=0; i<strings.length; i++) {
            if (strings[i].equalsIgnoreCase(prodotto.getComposizione())) {
                spinnerComposizione.setSelection(i);
            }
        }

        liquido = rr.getLiquidi().getRecordByPosition(0);
        String availability = liquido.getNome().split(" ", 2)[0];
        if (availability.equals("❌")) availability += " DISPONIBILE SOLO SE ORDINATO UNA SETTIMANA PRIMA";
        else availability += " Disponibile";
        ((TextView) view.findViewById(R.id.availability)).setText("Disponibilità: "+availability);
        ((TextView) view.findViewById(R.id.textViewLiquidDescriptionDescrizione)).setText(liquido.getDescrione());
        ((TextView) view.findViewById(R.id.TextView_LiquidDescription_TipologiaTiro)).setText("TIPOLOGIA DI TIRO: "+rr.getTipoTiri().getRecordByPosition(0).getNome());
        ((TextView) view.findViewById(R.id.TextView_LiquidDescription_Produttore)).setText("PRODUTTORE: "+rr.getProduttori().getRecordByPosition(0).getNome());
        ((TextView) view.findViewById(R.id.textView_LiquidDescription_Linea)).setText("LINEA:  "+rr.getLinee().getRecordByPosition(0).getNome());



        final TextViewPrezzo textViewPrezzo = new TextViewPrezzo((TextView) view.findViewById(R.id.textView_LiquidDescription_prezzo));

        spinner.setOnItemSelectedListener(new SpinnerListener(prodotto, textViewPrezzo, spinner));

        prodotto.setOnActionListener(new OnActionListener() {
            @Override
            public void action() {
                textViewPrezzo.setText(prodotto.getPrezzo());
            }
        });

        editText = (EditText) view.findViewById(R.id.editText_liquidDescription_mlNicotina);
        textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.i(ILog.LOG_TAG, TAG +"TextWatcher: "+s.toString());
                double nicotina;
                try {
                    nicotina = Double.valueOf(s.toString());
                }
                catch (NumberFormatException exc) {
                    nicotina = 0;
                }
                prodotto.setNicotina(nicotina);
                Log.i(ILog.LOG_TAG, TAG +"TextWatcher: "+prodotto.getMgNicotina());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        editText.addTextChangedListener(textWatcher);


        final Button button = (Button) view.findViewById(R.id.button_LiquidDescription_aggiungiAlCarello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodotto.setComposizione((String) spinnerComposizione.getSelectedItem());
                Prodotto newProdotto = prodotto.clone();
                newProdotto.setOnActionListener(null);
                activity.prodotti.add(newProdotto);
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("AGGIUNTO AL CARELLO").setMessage("Il prodotto è stato aggiunto corettamente al carello");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        textViewPrezzo.setText(this.prodotto.getPrezzo());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        super.onCreateOptionsMenu(menu, inflater, actionBar);
        Log.i(ILog.LOG_TAG, TAG +"onCreateOptionsMenu");
        actionBar.setTitle(prodotto.getLiquido().getNome());
    }

    class SpinnerListener implements OnItemSelectedListener{
        Prodotto prod;
        TextViewPrezzo textViewPrez;
        Spinner spinner;
            public SpinnerListener(Prodotto prodotto, TextViewPrezzo textViewPrez, Spinner spinner) {
                super();
                this.prod = prodotto;
                this.textViewPrez = textViewPrez;
                this.spinner = spinner;
            }
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                prodotto.setBoccetta(BoccettaR.getRecordById(((IdString) spinner.getItemAtPosition(position)).id));
                Log.i(ILog.LOG_TAG, TAG +"SpinnerListener - onItemSelected: "+(BoccettaR.getRecordById(((IdString) spinner.getItemAtPosition(position)).id)).getMl());
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){}
    }

    public static LiquidDescriptionF getInstance(int idLiquido) {
        LiquidDescriptionF fragment = new LiquidDescriptionF();
        fragment.getArguments().putInt(LiquidDescriptionF.ID_LIQUIDO, idLiquido);
        return fragment;
    }

    @Override
    public String getTagF() {
        return TAG;
    }

    @Override
    public void onPause() {
        editText.removeTextChangedListener(textWatcher);
        prodotto.setOnActionListener(null);
        textWatcher = null;
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Log.i(ILog.LOG_TAG, TAG +"onDestroyView");
        prodotto.setOnActionListener(null);
        editText.setText("");
        editText.setFreezesText(false);
        super.onDestroyView();
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG + "OptionMenu Select");
        switch (item.getItemId()) {
            case R.id.availability:
                new AlertDialog.Builder(activity).setTitle("INFORMAZIONI").setMessage("Cambia la disponibilità?\nServono diritti amministratore")
                        .setPositiveButton("✔️", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeAvailability("✔️"); }
                        }).setNegativeButton("❌", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeAvailability("❌"); }})
                .setNeutralButton("NO", null).show();
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }
    private void changeAvailability(String s) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Liquido.KEY_NOME, s+" "+liquido.getNome().split(" ", 2)[1]);
        DB.getLiquidDatabase().update(Liquido.NOME_TABELLA, initialValues, Liquido.KEY_RIGAID+"="+liquido.getId());
    }
}
