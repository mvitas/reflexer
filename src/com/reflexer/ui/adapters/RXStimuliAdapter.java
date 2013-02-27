package com.reflexer.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import antistatic.spinnerwheel.adapters.AbstractWheelAdapter;

import com.reflexer.R;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliDefinition;

import java.io.IOException;

public class RXStimuliAdapter extends AbstractWheelAdapter {

	private final Context context;

	public RXStimuliAdapter(Context context) {
		this.context = context;
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_stimuli, null);
		}

		RXStimuliDefinition definitino;
		try {
			definitino = RXStimuli.getStimuliDefinitions(context).get(index);

			TextView label = (TextView) convertView.findViewById(R.id.label);
			label.setText(definitino.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convertView;
	}
}
