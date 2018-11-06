package com.example.android.svapliquid.Activity.activity;

import android.os.Parcelable;

/**
 * Created by Andorid on 19/08/2017.
 */

interface ParcelableI {
    String getTag();
    Parcelable getParcelable();
    void restoreParcelable(Parcelable parcelable);
}
