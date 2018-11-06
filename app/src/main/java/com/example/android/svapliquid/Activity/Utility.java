package com.example.android.svapliquid.Activity;


import android.os.Environment;

import java.io.File;

/**
 * Created by Andorid on 28/07/2017.
 */

public class Utility {
    public static final String TAG = "Utility - ";
    public static final String directory = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SvapLiquid Ordini" + File.separator;
    public static double castDecimal(double value, int decimal) {
        return Math.floor(value*( Math.pow(10, decimal) )) / Math.pow(10,decimal);
    }
    public static double castVirgola(double value, double scarto) {
        //Log.i(ILog.LOG_TAG, TAG+ "castVirgola="+value);
        double intero = (int) value;
        //Log.i(ILog.LOG_TAG, TAG + "getPrezzoData: "+intero);
        double virgola = value-intero;
        //Log.i(ILog.LOG_TAG, TAG + "virgola: " + virgola);
        if (virgola>(scarto/2)) {
            if (virgola<(scarto+(scarto/2))) {
                //Log.i(ILog.LOG_TAG, TAG + "getPrezzoData: " + virgola);
                intero += scarto;
            } else {
                intero += (scarto*2);
            }
        }
        return intero;
    }
}
