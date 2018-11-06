package com.example.android.svapliquid.Activity.Ordin.controller.defaultController;

import android.util.ArrayMap;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.Ordin.UI.recordUI.defaultRecordUI.RecordUI;
import com.example.android.svapliquid.Activity.Ordin.index.OrdineKey;
import com.example.android.svapliquid.Activity.Ordin.record.defaultRecord.Record;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query.QueryString;

import java.util.ArrayList;

/**
 * Created by Andorid on 12/11/2017.
 */

public abstract class RecordControllers<R extends Record, U extends RecordUI<R>, I extends RecordController<R, U>> extends Controllers<I> implements NotifyDataSetChanged {
    private final ArrayList<I> controllers = new ArrayList<>();
    private DB.Database database;
    private QueryString queryString;

    public RecordControllers(DB.Database database, QueryString queryString, String nameController) {
        this.database = database;
        this.queryString = queryString;
        ControllerGuiController.addController(nameController, this);
        ControllerGuiController.refresh(nameController);
    }
    public abstract I getController(CursorS cursorS);
    public DB.Database getDatabase() {
        return database;
    }
    public R getRecord(int position) {
        return this.controllers.get(position).getRecord();
    }
    public U getRecordUI(int position) {
        return this.controllers.get(position).getRecordUI();
    }
    @Override
    public I get(int index) {
        try {
            return controllers.get(index);
        } catch (IndexOutOfBoundsException exp) {
            return null;
        }
    }


    @Override
    public boolean isEmpty() {
        return controllers.isEmpty();
    }

    @Override
    public int size() {
        return controllers.size();
    }

    public void setQueryString(QueryString query) {
        this.queryString = query;
    }
    public void notifyDataSetChanged() {
        CursorS cursorS = this.queryString.run(this.database);
        controllers.clear();
        while (cursorS.moveToNext()) {
            controllers.add(getController(cursorS));
        }
    }


    public void delete(ArrayList<I> itemsDeleted) {
        for (int i=0; i<itemsDeleted.size(); i++) {
            delete(controllers.indexOf(itemsDeleted.get(i)));
        }
    }
    public abstract boolean delete(int id);


    public static class ControllerGuiController {
        private static ArrayMap<String, ControllerGuiController> controllers;
        private final ArrayList<NotifyDataSetChanged> guiNotificationDataChange = new ArrayList<>(1);
        private final ArrayList<NotifyDataSetChanged> controllerNotificationDataChange = new ArrayList<>(1);

        private ControllerGuiController() {}

        private static ControllerGuiController getController(String nameController) {
            if (controllers == null) {
                controllers = new ArrayMap<>(1);
                controllers.put(nameController, new ControllerGuiController());
            } else {
                if (!controllers.containsKey(nameController)) {
                    controllers.put(nameController, new ControllerGuiController());
                }
            }
            return controllers.get(nameController);
        }

        public static void refresh(String nameController) {
            for (int i = 0; i < getController(nameController).controllerNotificationDataChange.size(); i++) {
                controllers.get(nameController).controllerNotificationDataChange.get(i).notifyDataSetChanged();
            }
            for (int i = 0; i < getController(nameController).guiNotificationDataChange.size(); i++) {
                controllers.get(nameController).guiNotificationDataChange.get(i).notifyDataSetChanged();
            }
        }

        public static void addController(String nameController, NotifyDataSetChanged notifyDataSetChanged) {
            if (!getController(nameController).controllerNotificationDataChange.contains(notifyDataSetChanged)) getController(nameController).controllerNotificationDataChange.add(notifyDataSetChanged);
        }
        public static void addGui(String nameController, NotifyDataSetChanged notifyDataSetChanged) {
            if (!getController(nameController).guiNotificationDataChange.contains(notifyDataSetChanged)) getController(nameController).guiNotificationDataChange.add(notifyDataSetChanged);
        }
        public static void destroy(String nameController) {
            try {
                controllers.get(nameController).controllerNotificationDataChange.clear();
            } catch (NullPointerException exc) {
                try {
                    controllers.get(nameController).guiNotificationDataChange.clear();
                } catch (NullPointerException exc3) {}
            }
            controllers.clear();
            controllers = null;
        }
    }
}
