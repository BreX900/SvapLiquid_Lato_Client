package com.example.android.svapliquid.Activity.Ordin.controller.account;

import android.content.ContentValues;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.AccountRecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.data.AccountData;
import com.example.android.svapliquid.Activity.Ordin.data.OrdineData;
import com.example.android.svapliquid.Activity.Ordin.index.AccountKey;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.record.AccountRecord;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

/**
 * Created by Andorid on 13/11/2017.
 */

public class AccountsController extends RecordControllers<AccountRecord, AccountRecordUI, AccountController> {

    public AccountsController(DB.Database database, QueryString queryString) {
        super(database, queryString, AccountKey.NOME_TABELLA);
    }

    public AccountsController(DB.Database database) {
        this(database, QueryString.getDefaultQuery(AccountKey.NOME_TABELLA));
    }

    @Override
    public AccountController getController(CursorS cursorS) {
        return new AccountController(cursorS);
    }

    @Override
    public boolean delete(int position) {
        return false;
    }

    public static long create(DB.Database database, AccountData account) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(AccountKey.ID, database.count(AccountKey.NOME_TABELLA));
        initialValues.put(AccountKey.NOME, account.getNome());
        initialValues.put(AccountKey.ADMINISTRATOR, account.isAdministrator());
        return database.insert(AccountKey.NOME_TABELLA, null, initialValues);
    }
    public static AccountController read(DB.Database database, long idAccount) {
        CursorS cursorS = new QueryString().addSelectAll().addFrom(AccountKey.NOME_TABELLA).addWhereAnd(AccountKey.ID+"="+idAccount).run(database);
        cursorS.moveToFirst();
        return new AccountController(cursorS);
    }

    public static AccountController updateAndRead(DB.Database database, AccountData accountData) {
        long id = create(database, accountData);
        return read(database, id);
    }
}
