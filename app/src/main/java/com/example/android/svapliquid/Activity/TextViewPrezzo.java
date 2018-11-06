package com.example.android.svapliquid.Activity;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Andorid on 28/07/2017.
 */

public class TextViewPrezzo {
    private final TextView textView;
    public TextViewPrezzo(TextView textView) {
        this.textView = textView;
    }

    public TextViewPrezzo(View view) {
        this((TextView) view);
    }

    public void setText(double prezzo) {
        this.setText(Utility.castDecimal(prezzo, 2)+"€");
    }

    public void setText(String text) {
        this.textView.setText(text);
    }
}
