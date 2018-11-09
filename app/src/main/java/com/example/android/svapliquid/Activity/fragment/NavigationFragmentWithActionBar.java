package com.example.android.svapliquid.Activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.activity.NavigationActivity;
import com.example.android.svapliquid.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 16/08/2017.
 */

public abstract class NavigationFragmentWithActionBar<E extends NavigationActivity> extends NavigationFragment<E> {
    public static final String TAG = "NavigationFragmentWithActionBar";
    ActionBar actionBar;
    @Deprecated
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(ILog.LOG_TAG, TAG +".."+this.getTagF()+": onCreateView");
        View view = this.createView(inflater, container, savedInstanceState);
        this.activity.setSupportActionBar(getToolbar(view));
        actionBar = activity.getSupportActionBar();
        return view;
    }
    public abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Deprecated
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(ILog.LOG_TAG, TAG +".."+this.getTagF()+": CreateOptionsMenu");

        if (!this.isLoad()) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (this.isSubHome()) {
                actionBar.setHomeAsUpIndicator(R.drawable.menu);
            }
        }
        this.onCreateOptionsMenu(menu, inflater, actionBar);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater, ActionBar actionBar) {
        Log.i(ILog.LOG_TAG, TAG + this.getTagF()+": onCreateOptionsMenu+actionbar");
        actionBar.setTitle("SvapLiquid");
    }
    @NotNull
    public abstract Toolbar getToolbar(View view);


}
