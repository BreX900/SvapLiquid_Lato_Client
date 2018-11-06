package com.example.android.svapliquid.Activity.Ordin.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.android.svapliquid.Activity.Ordin.data.DateData;
import com.example.android.svapliquid.R;

/**
 * Created by Andorid on 20/11/2017.
 */

public class DateController {
    public static AlertDialog.Builder getDateDialog(final AppCompatActivity activity, final DateData dateData, final  DialogInterface.OnClickListener onClickListenerPositive, final  DialogInterface.OnClickListener onClickListenerNeutral) {
        final LayoutInflater inflater = activity.getLayoutInflater();
        final View view = inflater.inflate(R.layout.alert_dialog_select_date, null);
        final DatePicker datePicker_date = (DatePicker) view.findViewById(R.id.datePicker_alerDialogSelectDate_date);
        if (dateData.isSet()) {
            datePicker_date.updateDate(dateData.getYear(), dateData.getMonth(), dateData.getDay());
        }
        return new AlertDialog.Builder(activity).setView(view).setTitle("SELEZIONA DATA").setPositiveButton("SELEZIONA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateData.setDate(datePicker_date.getYear(), datePicker_date.getMonth(), datePicker_date.getDayOfMonth());
                if (onClickListenerPositive != null) onClickListenerPositive.onClick(dialog, which);
            }
        }).setNeutralButton("ANNULLA", onClickListenerNeutral);
    }
}
