package com.example.android.svapliquid.Activity.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Memorys;
import com.example.android.svapliquid.Activity.fragment.NavigationFragment;
import com.example.android.svapliquid.Activity.fragment.NavigationFragmentWithActionBar;

import java.util.ArrayList;


/**Linee guida per le activity*/
public abstract class NavigationActivity extends AppCompatActivity{
    FragmentBackManager fragmentBackManager;
    ArrayList<ParcelableI> listParceable = new ArrayList<>();

    public Memorys getMemorys() {return null;}

    public abstract void load();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        if (savedInstanceState != null) {
            for (int i=0; i < listParceable.size(); i++) {
                ParcelableI parcelableI = listParceable.get(i);
                parcelableI.restoreParcelable(savedInstanceState.getParcelable(parcelableI.getTag()));
            }
        }
        super.onCreate(savedInstanceState, persistentState);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        for (int i=0; i < listParceable.size(); i++) {
            ParcelableI parcelableI = listParceable.get(i);
            outState.putParcelable(parcelableI.getTag(), parcelableI.getParcelable());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }
    public void onFragmentOpenedToBackStack(NavigationFragment navigationFragment) {
        this.fragmentBackManager.onFragmentOpenedToBackStack(navigationFragment);
    }
    public void onFragmentOpenedToBackStack(NavigationFragmentWithActionBar navigationFragment) {
        this.fragmentBackManager.onFragmentOpenedToBackStack(navigationFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        Log.i(ILog.LOG_TAG, "NavigationActivity - CreateOptionsMenu");
        this.fragmentBackManager.onCreateOptionsMenu(menu, this.getMenuInflater());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(ILog.LOG_TAG, "OptionItemSelected");
       return fragmentBackManager.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentBackManager.isBackPressed())
            super.onBackPressed();

    }
}
