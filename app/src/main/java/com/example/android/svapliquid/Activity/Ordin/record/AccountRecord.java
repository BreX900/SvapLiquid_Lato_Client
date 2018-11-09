package com.example.android.svapliquid.Activity.Ordin.record;

import com.example.android.svapliquid.Activity.Ordin.data.AccountData;
import com.example.android.svapliquid.Activity.Ordin.index.AccountKey;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;
import com.example.android.svapliquid.Activity.databases.CursorS;

/**
 * Created by Andorid on 13/11/2017.
 */

public class AccountRecord implements Record {
    final int id;
    final AccountData account;

    public AccountRecord(CursorS c) {
        this.id = c.getInt(AccountKey.ID);
        this.account = new AccountData(c.getString(AccountKey.NOME), c.getBoolean(AccountKey.ADMINISTRATOR));
    }

    @Override
    public int getId() {
        return id;
    }
    public String getNome() {
        return account.getNome();
    }
    public boolean isAdministrator() {
        return account.isAdministrator();
    }
}
