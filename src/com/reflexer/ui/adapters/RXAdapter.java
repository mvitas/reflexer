package com.reflexer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reflexer.R;
import com.reflexer.model.RXReflex;

import java.util.ArrayList;

public class RXAdapter extends ArrayAdapter<RXReflex> {

	public RXAdapter(Context context, ArrayList<RXReflex> items) {
		super(context, R.layout.list_item_rx, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_rx, null);
		}

		TextView rxName = (TextView) convertView.findViewById(R.id.rx_name_label);
		// CheckBox rxCheckbox =
		// (CheckBox)convertView.findViewById(R.id.rx_checkbox);

		rxName.setText(getItem(position).getName());
		// rxCheckbox.setChecked(getItem(position).isEnabled());

		return convertView;
	}

}
