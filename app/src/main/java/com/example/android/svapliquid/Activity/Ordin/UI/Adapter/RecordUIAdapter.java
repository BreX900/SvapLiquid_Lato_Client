package com.example.android.svapliquid.Activity.Ordin.UI.Adapter;

import android.widget.ListView;

import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.NotifyDataSetChanged;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordController;
import com.example.android.svapliquid.Activity.Ordin.controller.defaultController.RecordControllers;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Andorid on 08/11/2017.
 */

public abstract class RecordUIAdapter<R extends Record, U extends RecordUI<R>, I extends RecordController<R, U>>
        extends ListSelected<I, RecordControllers<R, U, I>> implements NotifyDataSetChanged{
    public RecordUIAdapter(@NotNull ListView listView, RecordControllers<R, U, I> recordControllers) {
        super(listView, recordControllers);
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    boolean isEmpty;

    @Override
    public int getCount() {
        int count;
        if ((count = list.size()) == 0) {
            isEmpty = true;
            count++;
        } else {
            isEmpty = false;
        }
        return count;
    }

    public U getItemRecordUI(int position) {
        return list.get(position).getRecordUI();
    }
    public R getItemRecord(int position) {
        return list.get(position).getRecord();
    }

    @Override
    public long getItemId(int position) {
        if (isEmpty) return 0;
        else return list.get(position).getRecord().getId();
    }
}
