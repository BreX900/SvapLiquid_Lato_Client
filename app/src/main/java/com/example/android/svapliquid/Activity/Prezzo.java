package com.example.android.svapliquid.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BaseR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta.RicettaR;

import java.io.Serializable;

/**
 * Created by Andorid on 05/08/2017.
 */

public class Prezzo implements Serializable, Cloneable{
    static final String TAG = "PrezzoData - ";

    private Prezzo(){}
    private static double getPrezzoBase (BoccettaR boccetta, BaseR base, double mlNicotina, RicettaR ricetta) {
        Log.i(ILog.LOG_TAG, TAG +"getPrezzoBase - prezzo:"+base.getPrezzo()+" - quantita:"+base.getQuantita()
                +" - ml:"+boccetta.getMl()+" - mlaroma:"+ricetta.getMlAromi(boccetta.getMl()));
        return (base.getPrezzo()/base.getQuantita()*(boccetta.getMl()-mlNicotina-ricetta.getMlAromi(boccetta.getMl())));
    }
    private static double getMlNicotina (BoccettaR boccetta, double mgNicotina, BaseR baseNicotinata) {
        return boccetta.getMl()/baseNicotinata.getMgSuMlNicotina()*mgNicotina;
    }
    private static double getPrezzoNicotina (BaseR baseNicotinata, double mlNicotina) {
        return (baseNicotinata.getPrezzo()/baseNicotinata.getQuantita()*mlNicotina);
    }
    private static double getPrezzoAroma(BoccettaR boccetta, RicettaR ricetta) {
        return ricetta.getPrezzoAromi(boccetta.getMl());
    }
    private static double getPrezzoExtra(RicettaR ricetta, BoccettaR boccetta) {
        Log.i(ILog.LOG_TAG, TAG +"getPrezzoExtra - 1:"+ricetta.getExtraPrezzo()+" - 2:"+(boccetta.getMl()*1.5));
        return ricetta.getExtraPrezzo()+(boccetta.getMl()/100*1.5);
    }
    private static double getPrezzoBoccetta(BoccettaR boccetta) {
        return boccetta.getPrezzo();
    }

    public static double getPrezzo(BoccettaR boccetta, BaseR base, double mgNicotina, BaseR baseNicotinata,  RicettaR ricetta) {
        Log.i(ILog.LOG_TAG, TAG +"-------------------------------------------------");
        double mlNicotina = getMlNicotina(boccetta, mgNicotina, baseNicotinata);
        Log.i(ILog.LOG_TAG, TAG +"getPrezzo - base:"+getPrezzoBase(boccetta, base, mlNicotina, ricetta)+" - ni:"+getPrezzoNicotina(baseNicotinata, mlNicotina)
        +" - ar:"+getPrezzoAroma(boccetta, ricetta)+" - ex:"+getPrezzoExtra(ricetta, boccetta)+" - bo:"+getPrezzoBoccetta(boccetta)+" - mlnicotina:"+mlNicotina);
        return getPrezzoBase(boccetta, base, mlNicotina, ricetta) + getPrezzoNicotina(baseNicotinata, mlNicotina) + getPrezzoAroma(boccetta, ricetta) + getPrezzoExtra(ricetta, boccetta) + getPrezzoBoccetta(boccetta) + ExtraChef.getExtraPrezzoChef();
    }
    static public class ExtraChef {
        static AppCompatActivity activity;
        static final String NAME_SHARED_PREFERENCES = "ExtraChef", KEY_EXTRA_PREZZO = "keyExtraPrezzo";
        static public void init(AppCompatActivity appCompatActivity) {
            activity = appCompatActivity;
        }
        static public void setExtraPrezzoChef(float extraPrezzo){
            try {
                if (activity == null)
                    throw new Exception("Not Inizializate ExtraChef: activity");
                SharedPreferences sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                if (extraPrezzo == 0) {
                    sharedPreferences.edit().remove(KEY_EXTRA_PREZZO).commit();
                } else {
                    sharedPreferences.edit().putFloat(KEY_EXTRA_PREZZO, extraPrezzo).commit();
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        static public float getExtraPrezzoChef() {
            try {
                if (activity == null)
                    throw new Exception("Not Inizializate ExtraChef: activity");
                SharedPreferences sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                if (sharedPreferences.contains(KEY_EXTRA_PREZZO)) {
                    return sharedPreferences.getFloat(KEY_EXTRA_PREZZO, 0);
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            return 0;
        }

        public static void setDefaultExtraPrezzoChef() {
            try {
                if (activity == null)
                    throw new Exception("Not Inizializate ExtraChef: activity");
                SharedPreferences sharedPreferences = activity.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(KEY_EXTRA_PREZZO).commit();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }
}