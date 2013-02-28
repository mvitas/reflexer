package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.reflexer.R;
import com.reflexer.model.RXReflex;

public class RXNameFragment extends Fragment {

	public interface OnNameChangedListener {
		public void onNameChanged(String name);
	}

	private EditText nameEdit;

	/**
	 * Reflex that is being edited.
	 */
	private RXReflex reflex;

	public static RXNameFragment newInstance() {
		RXNameFragment fragment = new RXNameFragment();

		return fragment;
	}

	public RXNameFragment() {
		super();
	}

	public void setReflex(RXReflex reflex) {
		this.reflex = reflex;

		if (!TextUtils.isEmpty(reflex.getName())) {
			nameEdit.setText(reflex.getName());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_name, null);

		nameEdit = (EditText) view.findViewById(R.id.edit_name);
		nameEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				reflex.setName(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		return view;
	}
}
