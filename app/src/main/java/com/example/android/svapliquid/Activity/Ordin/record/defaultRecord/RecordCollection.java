package com.example.android.svapliquid.Activity.Ordin.record.defaultRecord;

import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Andorid on 08/11/2017.
 */

public abstract class RecordCollection<E extends Record> extends ArrayList<E> {
    private final DB.Database database;
    private QueryString queryString;

    public RecordCollection(DB.Database database, QueryString queryString) {
        this.database = database;
        this.queryString = queryString;
        refresh();
    }

    public boolean addAll(CursorS c){
        while (c.moveToNext()) {
            add(getContainer(c));
        }
        return true;
    }

    @NotNull
    public abstract E getContainer(CursorS c);

    public void refresh() {
        clear();
        addAll(queryString.run(database));
    }

    public void refresh(QueryString queryString) {
        this.queryString = queryString;
        refresh();
    }

    public DB.Database getDatabase() {
        return database;
    }
}
