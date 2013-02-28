package com.reflexer.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.reflexer.R;

@SuppressLint("ResourceAsColor")
public class RXBoolView extends RXTypeView {

	private final CheckBox booleanValue;

	public RXBoolView(Context context) {
		super(context);

		LinearLayout content = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_rx_bool, null);
		addView(content, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		booleanValue = (CheckBox) content.findViewById(R.id.boolean_value);
		booleanValue.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				valueChanged(isChecked);
			}
		});
	}

	@Override
	public void setValue(Object value) {
		if (value.equals(Boolean.TRUE)) {
			booleanValue.setChecked(true);
		} else {
			booleanValue.setChecked(false);
		}
	}

	@Override
	public Object getValue() {
		return booleanValue.isChecked();
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		booleanValue.setText(name);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void setRequired(boolean required) {
		if (required) {
			// booleanValue.setTextColor(android.R.color.holo_red_dark);
		} else {
			// booleanValue.setTextColor(android.R.color.primary_text_dark);
		}
	}

	@Override
	public boolean getRequired() {
		return false; // TODO: not used
	}

}
