package com.example.android.svapliquid.Activity.fragment;

/**
 * Created by Android on 11/07/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.activity.NavigationActivity;

/**Da le linee guida per l'implementazione dei vari fragment*/
public abstract class NavigationFragment<A extends NavigationActivity> extends Fragment {
    public static final String TAG = "NavigationFragment - ";
    protected A activity;
    public NavigationFragment(){
        Bundle bundle = new Bundle();
        this.setArguments(bundle);
    }
    @Override
    public void onAttach(final Activity activity) {
        Log.i(ILog.LOG_TAG, TAG+"onAttach");

        this.activity= (A) activity;
        super.onAttach(activity);
    }



    abstract public String getTagF();
    public boolean isHome() {return false;}
    public boolean isLoad() {return false;}
}