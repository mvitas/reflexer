package com.reflexer.ui.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reflexer.R;

public class RXIntView extends RXTypeView {

	private final TextView nameTextView;

	private final EditText intValue;

	public RXIntView(Context context) {
		super(context);

		LinearLayout content = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_rx_int, null);
		addView(content, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		nameTextView = (TextView) content.findViewById(R.id.name);
		intValue = (EditText) content.findViewById(R.id.int_value);
		intValue.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				valueChanged(getValue());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void setValue(Object value) {
		intValue.setText(value.toString());
	}

	@Override
	public Object getValue() {
		return Integer.parseInt(intValue.getText().toString());
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		nameTextView.setText(name);
	}

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getRequired() {
		// TODO Auto-generated method stub
		return false;
	}

}
