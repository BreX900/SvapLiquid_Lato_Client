package com.example.android.svapliquid.Activity;

import java.util.HashMap;

/**
 * Created by Andorid on 08/08/2017.
 */

public class ValoriRicerca extends HashMap<String, String>{
   public static final String TAG = "ValoriRicerca";

    public boolean isSetValue(String key) {
        return containsKey(key) && get(key)!=null;
    }

    @Override
    public String put(String key, String value) {
        if (value!=null && !value.isEmpty())
            return super.put(key, value);
        return null;
    }
}
