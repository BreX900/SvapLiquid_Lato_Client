package com.example.android.svapliquid.Activity.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Andorid on 03/09/2017.
 * Richiamare il metodo onOptionsItemSelected nella classe utilizzata
 */

public class MenuManager {
    private final Menu menu;
    private final MenuInflater menuInflater;
    private final MenuLittleManager menuDefault;
    private MenuLittleManager menuUsed;


    public MenuManager(Menu menu, MenuInflater menuInflater, MenuLittleManager menuDefault) {
        this.menu = menu;
        this.menuInflater = menuInflater;
        this.menuUsed = menuDefault;
        this.menuDefault = menuDefault;
        setMenuDefault();
    }
    public void setMenuAndClear(MenuLittleManager menuLittleManager) {
        menu.clear();
        setMenu(menuLittleManager);
    }
    private void setMenu(MenuLittleManager menuLittleManager) {
        menuInflater.inflate(menuLittleManager.menuRes, menu);
        this.menuUsed = menuLittleManager;
    }
    public void setMenuDefault() {
        setMenuAndClear(menuDefault);
    }

    public boolean isSetMenuDefault() {
        return menuDefault==menuUsed;
    }
    public boolean onOptionsItemSelected(final MenuItem item) {
        return this.menuUsed.onOptionsItemSelectedListener.onOptionsItemSelected(item);
    }

    public static class MenuLittleManager {
        private final int menuRes;
        private final OnOptionsItemSelectedListener onOptionsItemSelectedListener;
        public MenuLittleManager(int menuRes, OnOptionsItemSelectedListener onOptionsItemSelectedListener) {
            this.menuRes = menuRes;
            this.onOptionsItemSelectedListener = onOptionsItemSelectedListener;
        }

    }
    public interface OnOptionsItemSelectedListener {
        boolean onOptionsItemSelected(final MenuItem item);
    }
}
