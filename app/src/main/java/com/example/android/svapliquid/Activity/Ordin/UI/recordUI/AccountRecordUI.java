package com.example.android.svapliquid.Activity.Ordin.UI.recordUI;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.record.AccountRecord;

/**
 * Created by Andorid on 13/11/2017.
 */

public class AccountRecordUI extends RecordUI<AccountRecord>{
    public AccountRecordUI(AccountRecord object) {
        super(object);
    }

    public String getNome() {
        return getItem().getNome();
    }
}
