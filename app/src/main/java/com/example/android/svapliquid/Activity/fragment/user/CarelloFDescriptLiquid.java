package com.example.android.svapliquid.Activity.fragment.user;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.OnActionListener;
import com.example.android.svapliquid.Activity.Prodotto;
import com.example.android.svapliquid.Activity.TextViewPrezzo;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BaseR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccettaR;
import com.example.android.svapliquid.Activity.fragment.Containers.IdString;
import com.example.android.svapliquid.Activity.fragment.Containers.SpinBaseAdapter;
import com.example.android.svapliquid.R;

/**
 * Created by Andorid on 05/08/2017.
 */

public class CarelloFDescriptLiquid  extends DescriptionLiquidF {
    public final static String TAG = "CarelloFDescriptLiquid - ";
    public final static String PRODOTTO = "positionProdotto";
    Prodotto carelProdotto;
    Prodotto prodotto;
    EditText editText;
    TextWatcher textWatcher;

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liquid_description, container, false);

        Log.i(ILog.LOG_TAG, TAG +"CreateLayout");
        Bundle bundle = this.getArguments();
        if (bundle.containsKey(PRODOTTO)) {
            prodotto = (activity.prodotti.get(bundle.getInt(PRODOTTO))).clone();
            carelProdotto = activity.prodotti.get(bundle.getInt(PRODOTTO));
        }
        else {
            Log.i(ILog.LOG_TAG, TAG +"onCreateView: negli argomenti manca: "+PRODOTTO);
        }

        final Spinner spinnerComposizione = (Spinner) view.findViewById(R.id.spinner_liquidDescription_composizione);
        spinnerComposizione.setSaveEnabled(false);
        ArrayAdapter<CharSequence> adapterSpinnerComposizione = ArrayAdapter.createFromResource(activity, R.array.composizione_array, android.R.layout.simple_spinner_item);
        adapterSpinnerComposizione.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComposizione.setAdapter(adapterSpinnerComposizione);

        final String[] strings = getResources().getStringArray(R.array.composizione_array);

        if (prodotto.isEditableComposizione()) {
            spinnerComposizione.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    prodotto.setComposizione(strings[spinnerComposizione.getSelectedItemPosition()]);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        } else {
            spinnerComposizione.setEnabled(false);
        }

        for (int i=0; i<strings.length; i++) {
            if (strings[i].equalsIgnoreCase(prodotto.getComposizione())) {
                spinnerComposizione.setSelection(i);
            }
        }


        final RisultatiRicerca rr = new RisultatiRicerca();
        rr.getAllTableByIdLiquid(prodotto.getLiquido().getId());

        ((TextView) view.findViewById(R.id.textViewLiquidDescriptionDescrizione)).setText(rr.getLiquidi().getRecordByPosition(0).getDescrione());
        ((TextView) view.findViewById(R.id.TextView_LiquidDescription_TipologiaTiro)).setText("TIPOLOGIA DI TIRO: "+rr.getTipoTiri().getRecordByPosition(0).getNome());
        ((TextView) view.findViewById(R.id.TextView_LiquidDescription_Produttore)).setText("PRODUTTORE: "+rr.getProduttori().getRecordByPosition(0).getNome());
        ((TextView) view.findViewById(R.id.textView_LiquidDescription_Linea)).setText("LINEA:  "+rr.getLinee().getRecordByPosition(0).getNome());

        final TextViewPrezzo textViewPrezzo = new TextViewPrezzo((TextView) view.findViewById(R.id.textView_LiquidDescription_prezzo));
        textViewPrezzo.setText(this.prodotto.getPrezzo());
        this.prodotto.setOnActionListener(new OnActionListener() {
            @Override
            public void action() {
                textViewPrezzo.setText(prodotto.getPrezzo());
            }
        });
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_liquidDescription_idBoccetta);
        spinner.setAdapter(new SpinBaseAdapter(activity, rr.getBoccette().getIdStrings(rr.getBoccette())));
        Log.i(ILog.LOG_TAG, TAG +"onCreateView: "+rr.getBoccette().getPositionById(prodotto.getBoccetta().getId()));
        spinner.setSelection(rr.getBoccette().getPositionById(prodotto.getBoccetta().getId()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prodotto.setBoccetta(BoccettaR.getRecordById(((IdString) spinner.getItemAtPosition(position)).id));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });



        editText = (EditText) view.findViewById(R.id.editText_liquidDescription_mlNicotina);
        editText.setFreezesText(false);
        editText.setText(String.valueOf((int)prodotto.getMgNicotina()), TextView.BufferType.EDITABLE);
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
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
        editText.addTextChangedListener(textWatcher);


        final BaseR base = rr.getBasi().getBasiConNicotina().getRecordByPosition(0);
        final Button button = (Button) view.findViewById(R.id.button_LiquidDescription_aggiungiAlCarello);
        button.setText("Salva");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.removeTextChangedListener(textWatcher);
                prodotto.setOnActionListener(null);
                activity.prodotti.remove(carelProdotto);
                prodotto.setComposizione((String) spinnerComposizione.getSelectedItem());
                activity.prodotti.add(prodotto);
                editText.setText(null);
                editText.setFreezesText(false);
                activity.onBackPressed();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        if (editText != null) editText.setText(String.valueOf((int)prodotto.getMgNicotina()), TextView.BufferType.EDITABLE);
        super.onResume();
    }

    public static CarelloFDescriptLiquid getInstance(int position) {
        CarelloFDescriptLiquid fragment = new CarelloFDescriptLiquid();
        fragment.getArguments().putInt(CarelloFDescriptLiquid.PRODOTTO, position);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, ActionBar actionBar) {
        inflater.inflate(R.menu.fragment_carello_descript_liquid_menu_option, menu);
        actionBar.setTitle(prodotto.getLiquido().getNome());
        Log.i(ILog.LOG_TAG, TAG +"CreateOptionsMenu");
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG +"OptionMenu Select");
        switch (item.getItemId()) {
            case R.id.menuOption1_fragmentCarelloLiquidDescript:
                activity.prodotti.remove(carelProdotto);
                activity.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        editText.setText(null);
        editText.setFreezesText(false);
        editText.removeTextChangedListener(textWatcher);
        prodotto.setOnActionListener(null);
        Log.i(ILog.LOG_TAG, TAG +"OptionMenu Select");
        super.onPause();
    }

    @Override
    public String getTagF() {
        return TAG;
    }

}

