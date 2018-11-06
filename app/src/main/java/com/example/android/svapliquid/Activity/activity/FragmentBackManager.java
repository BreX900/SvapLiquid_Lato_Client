package com.example.android.svapliquid.Activity.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.fragment.NavigationFragment;
import com.example.android.svapliquid.R;

import java.util.ArrayList;

/**
 * Created by Andorid on 16/08/2017.
 */

public class FragmentBackManager {
    public static final String TAG = "FragmentBackManager - ";
    private final ValueBack valueBack = new ValueBack();
    private DrawerLayout draweMenu;
    private final NavigationActivity navigationActivity;
    private final FragmentManager fragmentManager;
    private ArrayList<NavigationFragment> listFragment = new ArrayList<>(3);
    private NavigationFragment fragment;

    public FragmentBackManager(NavigationActivity navigationActivity, DrawerLayout drawerLayout) {
        this(navigationActivity);
        this.draweMenu = drawerLayout;
        drawerLayout.addDrawerListener(new DrawerMenuListener());
    }
    public FragmentBackManager(NavigationActivity navigationActivity) {
        this.navigationActivity = navigationActivity;
        this.fragmentManager = navigationActivity.getSupportFragmentManager();
    }


    public void onFragmentOpenedToBackStack(NavigationFragment frag) {
        final String tagFragment = frag.getTagF();
        Log.i(ILog.LOG_TAG, TAG + "onFragmentOpenedToBackStack: "+tagFragment);
        Fragment fragment = fragmentManager.findFragmentByTag(tagFragment);
        NavigationFragment navigationFragment;
        if (fragment == null) {
            navigationFragment = frag;
        } else {
            navigationFragment = (NavigationFragment) fragment;
        }
        if (this.fragment != null && this.fragment.isLoad()) {
            this.navigationActivity.load();
        }

        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction().replace(R.id.container_activity, navigationFragment, tagFragment);
        if (navigationFragment.isHome()) {
            if (this.draweMenu != null && navigationFragment.isLoad()) {
                this.draweMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
            else {
                this.draweMenu.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
            fragmentManager.popBackStack();
            navigationFragment.setRetainInstance(true);
            this.valueBack.setHome();
            listFragment = new ArrayList<>(3);
        }
        else {
            fragmentTransaction.addToBackStack(tagFragment);
            this.valueBack.addBack();
        }
        fragmentTransaction.commit();
        if (this.draweMenu != null && this.draweMenu.isDrawerOpen(GravityCompat.START)) {
            this.draweMenu.closeDrawer(GravityCompat.START);
        }
        this.fragment = navigationFragment;
        listFragment.add(navigationFragment);
    }
    /*public void onFragmentOpenedToBackStack(NavigationFragmentWithActionBar frag) {
        onFragmentOpenedToBackStack((NavigationFragment) frag);
    }*/

    public boolean isBackPressed() {
        boolean value = this.isBackPressedSimple();
        if (this.draweMenu != null) {
            value = this.isBackPressedWithDrawerMenu(value);
        }
        this.valueBack.lessBack();
        //Log.i(ILog.LOG_TAG, TAG + "isBackPressed: "+value+" - "+valueBack.toString());
        if (value && !valueBack.isExitStatus() && !this.valueBack.isStopStatus()) {
            Log.i(ILog.LOG_TAG, TAG +"isBackPressed:"+listFragment.size());
            listFragment.remove((listFragment.size() - 1));
            this.fragment = listFragment.get((listFragment.size() - 1));
        }
        return value;
    }
    boolean isBackPressedSimple() {
        if (this.valueBack.isAnother()) {
            return true;
        } else {
            if (this.valueBack.isHome()) {
                //Allerta uscita Toast
                return false;
            } else {
                return true;
            }
        }
    }
    boolean isBackPressedWithDrawerMenu(boolean value) {
        if (this.draweMenu.isDrawerOpen(GravityCompat.START)) { //Menu aperto? SI
            if (this.valueBack.isExitStatus()) {    //E' aperto perchè devi uscire? SI
                value = true;
            } else {    //NO, per altri motivi
                this.draweMenu.closeDrawer(GravityCompat.START);
                value = false;
            }
        } else {    //NO
            if (!value) {  //Sei nella home? se si apri il menu
                if (this.fragment.isLoad()) {
                    //NOTIFICA USCITA
                }else {
                    this.draweMenu.openDrawer(GravityCompat.START);
                }
            }
        }
        return value;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.i(ILog.LOG_TAG, TAG+".."+this.fragment.getTagF()+": onCreateOptionsMenu");
        this.fragment.onCreateOptionsMenu(menu, menuInflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.i(ILog.LOG_TAG, TAG+ "OptionItemSelected");
        if (item.getItemId() == android.R.id.home) {
            if (this.fragment.isHome())
                this.draweMenu.openDrawer(GravityCompat.START);
            else
                this.navigationActivity.onBackPressed();
            return true;
        } else
            return this.fragment.onOptionsItemSelected(item);
    }

    /*
    * 0> Altri fragment
    * 0 Primo Fragment anche chiamato Home
    * -1 Drawermenu aperto con OnBackPressed
    */
    class ValueBack {
        int valueBack = 0;

        void addBack() {
            this.valueBack++;
        }

        void lessBack() {
            this.valueBack--;
        }

        boolean isExitStatus() {
            return this.valueBack == -1;
        }

        boolean isHome() {
            return this.valueBack == 0;
        }

        boolean isAnother() {
            return this.valueBack > 0;
        }

        public void setHome() {
            this.valueBack = 0;
        }

        boolean isStopStatus() {return this.valueBack <-1;}
        @Override
        public String toString() {
            return "Value Back:"+this.valueBack;
        }
    }
    private class DrawerMenuListener implements DrawerLayout.DrawerListener {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {}
        @Override
        public void onDrawerOpened(View drawerView) {}
        @Override
        public void onDrawerClosed(View drawerView) {
            if (valueBack.isExitStatus()) {
                valueBack.addBack();
            }
        }
        @Override
        public void onDrawerStateChanged(int newState) {}
    }
}