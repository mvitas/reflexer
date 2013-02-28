package com.reflexer.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import antistatic.spinnerwheel.adapters.AbstractWheelTextAdapter;

import com.reflexer.R;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliDefinition;

import java.io.IOException;

public class RXStimuliAdapter extends AbstractWheelTextAdapter {

	public RXStimuliAdapter(Context context) {
		super(context, R.layout.list_item_stimuli, R.id.label);
	}

	@Override
	public int getItemsCount() {
		try {
			return RXStimuli.getStimuliDefinitions(context).size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		View view = super.getItem(index, convertView, parent);
		return view;
	}

	@Override
	protected CharSequence getItemText(int index) {
		try {
			RXStimuliDefinition definition = RXStimuli.getStimuliDefinitions(context).get(index);
			return "text";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
