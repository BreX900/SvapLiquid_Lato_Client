package com.example.android.svapliquid.Activity.Ordin.UI.Adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.android.svapliquid.Activity.Ordin.data.defaultData.List;
import com.example.android.svapliquid.Activity.fragment.MenuManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by Andorid on 14/08/2017.
 * Istanziare la lista e subito dopo istanziare questo adapter per poi...
 * Settare questo oggetto come adapter all'oggeto nel costruttore
 */

public abstract class ListSelected<I extends Object, L extends List<I>> extends BaseAdapter implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    L list;
    private ArrayList<I> selectList = new ArrayList<>();
    int position;
    MenuManager menuManager;
    MenuManager.MenuLittleManager menu;
    AdapterView.OnItemClickListener onClickListener;

    public ListSelected(@NotNull final ListView listView, L containerUIList) {
        this.list = containerUIList;
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
    }
    void notifyItemSelect(final int position) {
        this.position = position;
        if (selectList.contains(list.get(position))) {
            selectList.remove(list.get(position));
        } else {
            selectList.add(list.get(position));
        }
        menuDataChange();
        super.notifyDataSetChanged();
    }
    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        notifyItemSelect(position);
        return true;
    }
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (isSelectAnyItem()) {
            notifyItemSelect(position);
        } else {
            if (onClickListener != null) onClickListener.onItemClick(parent, view, position, id);
        }
    }
    /**Utilizzare questo metodo al posto di quello della lista*/
    public void setOnItemClickListener(final AdapterView.OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    boolean isSelected(int position) {
        if (list.size() > position) return this.selectList.contains(list.get(position));
        else return false;
    }

    @Deprecated
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        boolean selected = this.isSelected(position);
        View view = this.getView(position, convertView, parent, selected);
        this.changeViewSelected(view, selected, position);
        return view;
    }
    public abstract View getView(int position, View convertView, ViewGroup parent, boolean selected);


    public int colorAlternativ(boolean selected, int position) {
        if (selected) return Color.LTGRAY;
        else return Color.TRANSPARENT;
    }

    private void changeViewSelected(View view, boolean selected, int position) {
        view.setBackgroundColor(colorAlternativ(selected, position));
    }

    /**@Override
        public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater, ActionBar actionBar) {
        this.menuManager = new MenuManager(menu, inflater, new MenuManager.MenuLittleManager(R.menu.fragment_ordini_menu_option,
            new MenuManager.OnOptionsItemSelectedListener() {
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {}
           }));
        adapter.setMenuLongClick(menuManager, new MenuManager.MenuLittleManager(R.menu.layout_list,
           new MenuManager.OnOptionsItemSelectedListener() {
               @Override
               public boolean onOptionsItemSelected(MenuItem item) {}
           }));
        }
     */

    public void setMenuLongClick(MenuManager menuManager, MenuManager.MenuLittleManager menuLittleManager) {
        this.menuManager = menuManager;
        this.menu = menuLittleManager;
    }

    public ArrayList<I> getItemsSelected() {
        return selectList;
    }

    public boolean isSelectAnyItem() {
        return !selectList.isEmpty();
    }

    @Override
    public I getItem(int position) {
        return list.get(position);
    }

    private void menuDataChange() {
        if (selectList.isEmpty()) {
            if (this.menuManager != null)
                this.menuManager.setMenuDefault();
        } else {
            if (this.menuManager != null && this.menuManager.isSetMenuDefault())
                this.menuManager.setMenuAndClear(menu);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        selectList.clear();
        menuDataChange();
        super.notifyDataSetChanged();
    }
}
