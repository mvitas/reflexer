package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.reflexer.R;

public class RXNameFragment extends Fragment {

	public interface OnNameChangedListener {
		public void onNameChanged(String name);
	}

	private EditText nameEdit;

	/**
	 * Name of the reflex that is being created.
	 */
	private String name;

	public static RXNameFragment newInstance() {
		RXNameFragment fragment = new RXNameFragment();

		return fragment;
	}

	public static RXNameFragment newInstance(String name) {
		RXNameFragment fragment = new RXNameFragment(name);
		return fragment;
	}

	public RXNameFragment() {
		super();
	}

	public RXNameFragment(String name) {
		this.name = name;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_name, null);

		nameEdit = (EditText) view.findViewById(R.id.edit_name);
		nameEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		if (name != null) {
			nameEdit.setText(name);
		}

		return view;
	}
}
