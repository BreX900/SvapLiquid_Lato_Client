package com.example.android.svapliquid.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andorid on 20/11/2017.
 */

public class SP {
    private final static String DEFAULT_SP = "defaulSP";
    private SharedPreferences sharedPreferencesDefault;

    public static SharedPreferences getSharedPreferencesDefault(AppCompatActivity activity) {
        return activity.getSharedPreferences(DEFAULT_SP, Context.MODE_PRIVATE);
    }

    public interface KEY {
        String ACCOUNT_POSITION = "accountDefault";
    }
}
