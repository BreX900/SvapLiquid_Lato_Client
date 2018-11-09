package com.example.android.svapliquid.Activity.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.svapliquid.Activity.Help;
import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Memorys;
import com.example.android.svapliquid.Activity.Ordin.controller.account.AccountController;
import com.example.android.svapliquid.Activity.Prodotti;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.fragment.administrator.OrdiniF;
import com.example.android.svapliquid.Activity.fragment.ricerca.SubMainF;
import com.example.android.svapliquid.Activity.fragment.user.AllLiquidF;
import com.example.android.svapliquid.Activity.fragment.user.CarelloF;
import com.example.android.svapliquid.Activity.fragment.user.MainActivityF;
import com.example.android.svapliquid.R;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends NavigationActivity implements NavigationView.OnNavigationItemSelectedListener{
    static public final String TAG = "MainActivity - ";
    private final static String KEY_FRAGMENTS = "Fragments";
    private final static String KEY_BACK_ON = "OnBack";
    private int backOn = 1;

    protected static final String NAME_FILE = "carello";
    protected static String CARELLO_NAME_FILE = "";
    public Prodotti prodotti;
    public AccountController account;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.checkPermission();
        DB.setContext(this);
        setContentView(R.layout.activity_main);

        //(this.drawerLayout= (DrawerLayout) findViewById(R.id.MainActivity_DrawerLayout)).addDrawerListener(new DrawerListenerA());
        ((NavigationView) findViewById(R.id.MainActivity_NavigationView)).setNavigationItemSelectedListener(this);
        fragmentBackManager = new FragmentBackManager(this, (DrawerLayout) findViewById(R.id.MainActivity_DrawerLayout));


        if (savedInstanceState == null) {
            Log.i(ILog.LOG_TAG, MainActivity.TAG+ "if");
            this.onFragmentOpenedToBackStack(MainActivityF.getInstance());
            //this.memorys= new Memorys();
            //this.memorys.directoryInternal= new File[]{new File(File.pathSeparator +"ciao"), new File(File.pathSeparator +"ciao11")};
        }
        else {
            Log.i(ILog.LOG_TAG, MainActivity.TAG+ "else");
            this.backOn= savedInstanceState.getInt(MainActivity.KEY_BACK_ON, 1);
            //this.memorys= (Memorys) savedInstanceState.getSerializable(Memorys.KEY_MEMORYS);
            this.prodotti = (Prodotti) savedInstanceState.getSerializable(CARELLO_NAME_FILE);
        }

    }

    @Override
    protected void onRestart() {
        DB.setContext(this);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        DB.setContext(this);
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MainActivity.KEY_BACK_ON, this.backOn);
        //outState.putSerializable(Memorys.KEY_MEMORYS, this.memorys);
        //outState.putSerializable(MainActivity.KEY_FRAGMENTS, this.fragments);
        outState.putSerializable(CARELLO_NAME_FILE, this.prodotti);
        Log.i(ILog.LOG_TAG, MainActivity.TAG+ "onSaveInstanceState: ");
        try {
            prodotti.save(CARELLO_NAME_FILE);
            Log.i(ILog.LOG_TAG, "onDestroy: Carello salvato!: "+this.prodotti.size());
        }
        catch (Exception exc) {
            Log.i(ILog.LOG_TAG, "onDestroy: Carello non salvato!");
        }
        super.onSaveInstanceState(outState);
    }

    public boolean isAdministrator() {
        return (account != null && account.getRecord().isAdministrator());
    }
    void checkPermission() {
        while (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item1_0:
                this.onFragmentOpenedToBackStack(AllLiquidF.getInstance());
                break;
            case R.id.menu_item1_1:
                this.onFragmentOpenedToBackStack(SubMainF.getInstance());
                break;
            case R.id.menu_item1_2:
                this.onFragmentOpenedToBackStack(CarelloF.getInstance());
                break;
            case R.id.menu_item1_9_0:
                this.onFragmentOpenedToBackStack(OrdiniF.getInstance());
                break;
            case R.id.menu_item2_1:
                try {
                    prodotti.save(CARELLO_NAME_FILE);
                } catch (Exception exc){
                    exc.printStackTrace();
                }
                this.onFragmentOpenedToBackStack(MainActivityF.getInstance());
                break;
            case R.id.menu_item2_2:
                Help.showHelp(this, account);
                break;
        }
        return false;
    }
    @Override
    public void load() {
        CARELLO_NAME_FILE = NAME_FILE+"_"+this.account.getRecordUI().getNome();
        try {
            this.prodotti = Prodotti.load((CARELLO_NAME_FILE));
            Log.i(ILog.LOG_TAG, MainActivity.TAG+ "Load File carello: "+this.prodotti.size());
        }
        catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
            this.prodotti = new Prodotti();
        }
    }
    public String getNomeFileCarello() {
        return CARELLO_NAME_FILE;
    }
    ArrayList<Fragment> arrayList = new ArrayList<>();

    @Override
    public Memorys getMemorys() {
        return null;//this.memorys;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(ILog.LOG_TAG, "giriamo");
    }

    @Override
    protected void onPause() {
        DB.closeAll();
        if (prodotti != null && !prodotti.isEmpty()) {
            try {
                prodotti.save(CARELLO_NAME_FILE);
                Log.i(ILog.LOG_TAG, "onPause: Carello salvato!: " + this.prodotti.size());
            } catch (Exception exc) {
                exc.printStackTrace();
                Log.i(ILog.LOG_TAG, "onPause: Carello non salvato!");
            }
        }
        super.onPause();
    }
}
