package com.reflexer.ui.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reflexer.R;

public class RXStringView extends RXTypeView {

	private final TextView nameTextView;

	private final EditText stringValue;

	public RXStringView(Context context) {
		super(context);

		LinearLayout content = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_rx_string, null);
		addView(content, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		nameTextView = (TextView) content.findViewById(R.id.name);
		stringValue = (EditText) content.findViewById(R.id.string_value);
		stringValue.addTextChangedListener(new TextWatcher() {

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
		stringValue.setText(value.toString());
	}

	@Override
	public Object getValue() {
		return stringValue.getText().toString();
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		nameTextView.setText(name);
	}

	@Override
	public void setRequired(boolean required) {
		// TODO implement
	}

	@Override
	public boolean getRequired() {
		// TODO not used yet
		return false;
	}

}
