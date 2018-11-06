package com.example.android.svapliquid.Activity.Ordin.UI.Adapter;

import com.example.android.svapliquid.Activity.Ordin.UI.dafaultUI.ObjectUI;

/**
 * Created by Andorid on 12/11/2017.
 */

public interface ContainerUI<B extends Object, A extends ObjectUI<B>> {
    A getUI(int position);

    boolean isEmpty();
    int size();
}
