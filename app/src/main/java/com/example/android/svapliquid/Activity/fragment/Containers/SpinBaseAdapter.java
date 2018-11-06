package com.example.android.svapliquid.Activity.fragment.Containers;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andorid on 06/08/2017.
 */

public class SpinBaseAdapter extends BaseAdapter{
    private Context context;
    ArrayList<IdString> idStrings;

        public SpinBaseAdapter(Context context, ArrayList<IdString> idStrings) {
            super();
            this.context = context;
            this.idStrings = idStrings;
        }

        public int getCount() {
            return idStrings.size();
        }

        public IdString getItem(int position) {
            return idStrings.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.i(ILog.LOG_TAG, TAG + "getGroupView");
            //if (convertView == null) {
            //    LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //    convertView = infalInflater.inflate(R.layout.spinner_accounts, null);
            //}

            //TextView textView = (TextView) convertView.findViewById(R.id.textView_spinnerAccount_name);
            //textView.setText(getItem(position).getNome());
            //return convertView;

            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position).string);
            label.setTextSize(24);
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setTextColor(Color.BLACK);
            label.setText(getItem(position).string);
            label.setTextSize(20);
            label.setLineSpacing(1, 0);
            label.setLines(2);
            label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            return label;
        }
}
