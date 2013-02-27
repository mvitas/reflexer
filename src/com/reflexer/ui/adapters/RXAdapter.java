
package com.reflexer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.reflexer.R;
import com.reflexer.ui.dummy.RXItem;

import java.util.ArrayList;

public class RXAdapter extends BaseAdapter {

    Context context;
    ArrayList<RXItem> items;

    public RXAdapter(Context context, ArrayList<RXItem> items) {

        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    
    public RXItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_rx, null);
        }
        
        TextView rxName = (TextView)convertView.findViewById(R.id.rx_name_label);
//        CheckBox rxCheckbox = (CheckBox)convertView.findViewById(R.id.rx_checkbox);
        
        
        rxName.setText(getItem(position).getName());
//        rxCheckbox.setChecked(getItem(position).isEnabled());

        return convertView;
    }

}
