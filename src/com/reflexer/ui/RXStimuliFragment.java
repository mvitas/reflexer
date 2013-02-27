package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;

import com.reflexer.R;
import com.reflexer.model.RXStimuliDefinition;
import com.reflexer.ui.adapters.RXStimuliAdapter;

public class RXStimuliFragment extends Fragment {

	public static RXStimuliFragment newInstance() {
		RXStimuliFragment fragment = new RXStimuliFragment();

		return fragment;
	}

	public static RXStimuliFragment newInstance(RXStimuliDefinition definition) {
		RXStimuliFragment fragment = new RXStimuliFragment(definition);
		return fragment;
	}

	public RXStimuliFragment() {
		super();
	}

	/**
	 * Constructor that is called when editing an existing reflex. (stimuli is
	 * allready selected)
	 * 
	 * @param definition
	 */
	public RXStimuliFragment(RXStimuliDefinition definition) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stimuli, null);

		AbstractWheel stimuliWheel = (AbstractWheel) view.findViewById(R.id.stimuli);

		stimuliWheel.setViewAdapter(new RXStimuliAdapter(getActivity()));
		stimuliWheel.setVisibleItems(7);
		stimuliWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				Log.d("RXStimuliFragment", "OnWheelChanged: " + newValue);
			}
		});

		return view;
	}
}
