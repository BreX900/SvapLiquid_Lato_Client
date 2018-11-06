package com.example.android.svapliquid.Activity.Ordin.UI;

import com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI.ObjectUI;
import com.example.android.svapliquid.Activity.Ordin.data.DateData;

/**
 * Created by Andorid on 14/11/2017.
 */

public class DateUI extends ObjectUI<DateData> {
    public DateUI(DateData object) {
        super(object);
    }

    @Override
    public String toString() {
        return getDate();
    }



    public String getDate() {
        return getItem().getDay()+"/"+getItem().getMonth()+"/"+getItem().getYear();
    }
}
