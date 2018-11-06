package com.example.android.svapliquid.Activity.fragment.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.AccountRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.account.AccountController;
import com.example.android.svapliquid.Activity.Ordin.controller.account.AccountsController;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.NotifyDataSetChanged;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.data.AccountData;
import com.example.android.svapliquid.Activity.Ordin.index.AccountKey;
import com.example.android.svapliquid.Activity.Prezzo;
import com.example.android.svapliquid.Activity.Prodotti;
import com.example.android.svapliquid.Activity.SP;
import com.example.android.svapliquid.Activity.activity.MainActivity;
import com.example.android.svapliquid.Activity.activity.NavigationActivity;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.account_db.account.AccountR;
import com.example.android.svapliquid.Activity.databases.account_db.account.AccountT;
import com.example.android.svapliquid.Activity.fragment.IBackOn;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;
import com.example.android.svapliquid.Activity.fragment.ricerca.SubMainF;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

import static android.widget.AdapterView.INVALID_POSITION;


/**
 * E' la classe del fragment principale dell'app
 */
public class MainActivityF extends NavigationFragmentWithActionBar<MainActivity> implements IBackOn {
    public final static String TAG = "MainActivityF - ";
    AccountsController accountsController;
    SpinAdapter spinAdapter;
    Spinner spinner;

    NotifyDataSetChanged notifyDataSetChanged;
    AccountController.NotifyResultCreate notifyResultCreate;

    @Override
    public View createView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);
        Log.i(ILog.LOG_TAG, TAG + "CreateLayout");

        Prezzo.ExtraChef.init(activity);

        accountsController = new AccountsController(DB.getAccountDatabase());
        notifyDataSetChanged = new NotifyDataSetChanged() {
            @Override
            public void notifyDataSetChanged() {
                if (accountsController.isEmpty()) {
                    AccountController.create(DB.getAccountDatabase(), activity, "Per usare l'applicazione serve un account.", notifyResultCreate).setCancelable(false).show();
                }
            }
        };
        notifyResultCreate = new AccountController.NotifyResultCreate() {
            @Override
            public void notifyResult(long result) {
                RecordControllers.ControllerGuiController.refresh(AccountKey.NOME_TABELLA);
            }
        };
        RecordControllers.ControllerGuiController.addGui(AccountKey.NOME_TABELLA, notifyDataSetChanged);



        spinner = (Spinner) view.findViewById(R.id.spinner_fragmentMainActivity_userNames);
        spinAdapter = new SpinAdapter(activity, accountsController);
        RecordControllers.ControllerGuiController.addGui(AccountKey.NOME_TABELLA, spinAdapter);
        spinner.setAdapter(spinAdapter);

        final SharedPreferences sharedPreferences = SP.getSharedPreferencesDefault(activity);
        if (sharedPreferences.contains(SP.KEY.ACCOUNT_POSITION))
            spinner.setSelection(sharedPreferences.getInt(SP.KEY.ACCOUNT_POSITION, 0));

        final Button loginRegister = (Button) view.findViewById(R.id.buttton_fragmentMainActivity_loginRegister);
        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountsController.size() == 0) {
                    Toast.makeText(activity, "Nessun account esiste, crea un account.", Toast.LENGTH_LONG).show();
                } else {
                    activity.account = spinAdapter.getItem(spinner.getSelectedItemPosition());
                    NavigationView navigationView = (NavigationView) activity.findViewById(R.id.MainActivity_NavigationView);
                    if (navigationView.getMenu() != null) navigationView.getMenu().clear();
                    navigationView.getMenu().add(activity.account.getRecordUI().getNome().toUpperCase());
                    if (activity.isAdministrator()) navigationView.inflateMenu(R.menu.main_activity_menu_administrator);
                    else navigationView.inflateMenu(R.menu.main_activity_menu);
                    sharedPreferences.edit().putInt(SP.KEY.ACCOUNT_POSITION, spinner.getSelectedItemPosition()).apply();
                    activity.onFragmentOpenedToBackStack(AllLiquidF.getInstance());
                }
            }
        });
        notifyDataSetChanged.notifyDataSetChanged();
        return view;
    }

    public static NavigationFragmentWithActionBar getInstance() {
        MainActivityF imageSqueezeF = new MainActivityF();
        return imageSqueezeF;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, ActionBar actionBar) {
        inflater.inflate(R.menu.fragment_main_activity_menu_option, menu);
        Log.i(ILog.LOG_TAG, TAG + "CreateOptionsMenu");
    }

    @NotNull
    @Override
    public Toolbar getToolbar(View view) {
        return (Toolbar) view.findViewById(R.id.toolbar_fragmentMainActivity);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, TAG + "OptionMenu Select");
        switch (item.getItemId()) {
            case R.id.menuOption1_fragmentMainActivity:
                AccountController.create(DB.getAccountDatabase(), activity, "", notifyResultCreate).show();
                break;
            case R.id.menuOption2_fragmentMainActivity:
                if (accountsController.isEmpty()) {
                    Toast.makeText(activity, "Nessun account esiste.", Toast.LENGTH_LONG).show();
                } else {
                    if (spinner.getSelectedItemPosition() == INVALID_POSITION) {
                        Toast.makeText(activity, "Nessun account selezionato.", Toast.LENGTH_LONG).show();
                    } else {
                        final AccountController accountController = spinAdapter.getItem(spinner.getSelectedItemPosition());
                        AccountController.delete(DB.getAccountDatabase(), accountController, activity, new AccountController.NotifyResultDelete() {
                            @Override
                            public void notifyResult(boolean result) {
                                if (result) {
                                    Prodotti.removeFile(activity.getNomeFileCarello());
                                    Toast.makeText(activity, "Account "+accountController.getRecordUI().getNome()+" rimosso con successo.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(activity, "Account "+accountController.getRecordUI().getNome()+" non rimosso.", Toast.LENGTH_LONG).show();
                                }
                                RecordControllers.ControllerGuiController.refresh(AccountKey.NOME_TABELLA);
                            }
                        }).show();
                    }
                }
                break;
            case R.id.menuOption3_fragmentMainActivity:
                final View view_ExtraPrezzoChef = activity.getLayoutInflater().inflate(R.layout.alert_dialog_float, null);
                final EditText editText_prezzoExtraChef = (EditText) view_ExtraPrezzoChef.findViewById(R.id.editText_alertDialogFloat_prezzoExtraChef);
                editText_prezzoExtraChef.setText(Prezzo.ExtraChef.getExtraPrezzoChef()+"");
                new AlertDialog.Builder(activity).setView(view_ExtraPrezzoChef).setPositiveButton("SALVA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float number = 0;
                        try {
                            number = Float.parseFloat(editText_prezzoExtraChef.getText().toString());
                        } catch (NumberFormatException exc) {}
                        Prezzo.ExtraChef.setExtraPrezzoChef(number);
                    }
                }).setNegativeButton("ANNULLA", null).setNeutralButton("RESETTA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Prezzo.ExtraChef.setDefaultExtraPrezzoChef();
                    }
                }).show();

                break;
        }
        return true;
    }

    @Override
    public String getTagF() {
        return TAG;
    }
    @Override
    public boolean isHome() {
        return true;
    }
    @Override
    public boolean isLoad() {
        return true;
    }

    @Override
    public void onDestroyView() {
        RecordControllers.ControllerGuiController.destroy(AccountKey.NOME_TABELLA);
        super.onDestroyView();
    }


    public class SpinAdapter extends BaseAdapter implements NotifyDataSetChanged {
        private Context context;
        private AccountsController accountsController;

        public SpinAdapter(Context context, AccountsController accountsController) {
            super();
            this.context = context;
            this.accountsController = accountsController;
        }

        public int getCount() {
            return accountsController.size();
        }

        public AccountController getItem(int position) {
            return accountsController.get(position);
        }

        public long getItemId(int position) {
            return accountsController.getRecord(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.i(ILog.LOG_TAG, TAG + "getGroupView");
            //if (convertView == null) {
            //    LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //    convertView = infalInflater.inflate(R.layout.spinner_accounts, null);
            //}

            //TextView textView = (TextView) convertView.findViewById(R.id.textView_spinnerAccount_name);
            //textView.setText(getItem(position).getNome());
            //return convertView;
            AccountRecordUI accountRecordUI = getItem(position).getRecordUI();
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(accountRecordUI.getNome());
            label.setTextSize(24);
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position).getRecordUI().getNome());
            label.setTextSize(20);
            label.setLineSpacing(1, 0);
            label.setLines(2);
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return label;
        }
    }
}
