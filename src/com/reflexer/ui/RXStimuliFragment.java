package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;

import com.reflexer.R;
import com.reflexer.model.RXConditionDefinition;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.ui.adapters.RXStimuliAdapter;
import com.reflexer.ui.views.RXTypeView;
import com.reflexer.ui.views.RXTypeView.OnValueChangedListener;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This fragment is the visual representation of RXStimuli. It is used to select
 * which type of stimuli is used and to define corresponding coniditions.
 * 
 * 
 * @author ivan
 * 
 */
public class RXStimuliFragment extends Fragment {

	/**
	 * RXStimuli that is represented by this fragment.
	 */
	private RXStimuli stimuli;

	/**
	 * Layout that holds the views for defining the conditions.
	 */
	private LinearLayout conditionsLayout;

	private final ArrayList<RXTypeView> conditionViews = new ArrayList<RXTypeView>();

	public static RXStimuliFragment newInstance() {
		RXStimuliFragment fragment = new RXStimuliFragment();

		return fragment;
	}

	public static RXStimuliFragment newInstance(RXStimuli stimuli) {
		RXStimuliFragment fragment = new RXStimuliFragment(stimuli);
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
	public RXStimuliFragment(RXStimuli stimuli) {
		this.stimuli = stimuli;
		showConditions();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stimuli, null);

		AbstractWheel stimuliWheel = (AbstractWheel) view.findViewById(R.id.stimuli);
		stimuliWheel.setViewAdapter(new RXStimuliAdapter(getActivity()));
		// stimuliWheel.setVisibleItems(3);

		stimuliWheel.setCyclic(true);
		stimuliWheel.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
				Log.d("RXStimuliFragment", "OnWheelChanged: " + newValue);

				initStimuliFromDefinitions(newValue);
			}
		});

		conditionsLayout = (LinearLayout) view.findViewById(R.id.conditions_layout);

		if (stimuli == null) {
			initStimuliFromDefinitions(0);
		}

		return view;
	}

	private void initStimuliFromDefinitions(int index) {
		try {
			this.stimuli = new RXStimuli(RXStimuli.getStimuliDefinitions(getActivity()).get(index));
			showConditions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the conditions for the selected stimuli.
	 */
	private void showConditions() {
		conditionViews.clear();
		conditionsLayout.removeAllViews();

		for (RXConditionDefinition condDef : stimuli.getDefinition().getConditionDefinitons()) {
			RXTypeView conditionView = RXTypeView.createView(getActivity(), condDef.getName(), condDef.getType());
			conditionView.setRequired(condDef.isRequired());
			conditionView.setEnabled(condDef.getDependsOn().size() == 0);
			conditionView.setOnValueChangedListener(new OnValueChangedListener() {

				@Override
				public void onValueChanged(String name, Object value) {
					stimuli.setCondition(new RXStimuliCondition(name, value));

					updateConditions();
				}
			});

			conditionViews.add(conditionView);
			conditionViews.add(conditionView);
			conditionsLayout.addView(conditionView, new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			Log.d("showConditions", "added: " + condDef.getName());
		}
	}

	private void updateConditions() {
		for (RXTypeView typeView : conditionViews) {
			RXConditionDefinition condDef = stimuli.getDefinition().getConditionDefinitionByName(typeView.getName());

			if (condDef.getDependsOn().size() > 0) {
				typeView.setEnabled(true);

				for (RXConditionDefinition dependency : condDef.getDependsOn()) {
					RXStimuliCondition cond = stimuli.getConditionByName(dependency.getName());
					if (cond == null || cond.getValue() == null) {
						typeView.setEnabled(false);
						break;
					}
				}
			}
		}
	}
}
