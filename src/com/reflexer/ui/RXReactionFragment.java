package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.reflexer.R;
import com.reflexer.model.RXPropertyDefinition;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.ui.views.RXTypeView;
import com.reflexer.ui.views.RXTypeView.OnValueChangedListener;

import java.io.IOException;
import java.util.ArrayList;

public class RXReactionFragment extends Fragment {

	/**
	 * Reflex that is being updated or created.
	 */
	private RXReflex reflex;

	/**
	 * Index of the selected stimuli type in the stimuli definitions array.
	 */
	private int selectedIndex;

	private ImageView reactionImage;

	private TextView reactionName;

	private ArrayList<RXReactionDefinition> reactionDefinitions;

	private LinearLayout propertiesLayout;

	private final ArrayList<RXTypeView> propertyViews = new ArrayList<RXTypeView>();

	public static RXReactionFragment newInstance() {
		RXReactionFragment fragment = new RXReactionFragment();

		return fragment;
	}

	public RXReactionFragment() {
		super();
	}

	public void setReflex(RXReflex reflex) {
		this.reflex = reflex;

		updateReactionIndex(reflex);
		showReaction(selectedIndex);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_reaction, null);

		getReactionDefinitions();
		propertiesLayout = (LinearLayout) view.findViewById(R.id.properties_layout);

		reactionImage = (ImageView) view.findViewById(R.id.image);
		reactionName = (TextView) view.findViewById(R.id.label);

		setupReactionPicker(view);

		if (reflex != null) {
			showReaction(selectedIndex);
		}

		return view;
	}

	private void setupReactionPicker(View rootView) {
		ImageView leftReactionPicker = (ImageView) rootView.findViewById(R.id.reaction_picker_left);
		ImageView rightReactionPicker = (ImageView) rootView.findViewById(R.id.reaction_picker_right);

		leftReactionPicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedIndex = (selectedIndex - 1);
				if (selectedIndex < 0) {
					selectedIndex = reactionDefinitions.size() - 1;
				}

				updateReaction(selectedIndex);
				showReaction(selectedIndex);
			}
		});

		rightReactionPicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedIndex = (selectedIndex + 1) % reactionDefinitions.size();

				updateReaction(selectedIndex);
				showReaction(selectedIndex);
			}
		});
	}

	private void getReactionDefinitions() {
		try {
			this.reactionDefinitions = RXReaction.getReactionDefinitions(getActivity());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateReactionIndex(RXReflex reflex) {
		RXReactionDefinition def = reflex.getReaction().getDefinition();

		if (reactionDefinitions == null) {
			getReactionDefinitions();
		}

		for (int i = 0; i < reactionDefinitions.size(); i++) {
			if (reactionDefinitions.get(i).getName().equals(def.getName())) {
				selectedIndex = i;
				showReaction(selectedIndex);
			}
		}
	}

	private void updateReaction(int index) {
		RXReactionDefinition def = reactionDefinitions.get(index);
		RXReaction reaction = RXReaction.createReaction(def);
		reflex.setReaction(reaction);
	}

	private void showReaction(int index) {
		if (reactionName != null) {
			reactionName.setText(reactionDefinitions.get(index).getName());

			showProperties();
			updateProperties();
		}
	}

	private void showProperties() {
		propertyViews.clear();
		propertiesLayout.removeAllViews();

		for (RXPropertyDefinition propDef : reflex.getReaction().getDefinition().getPropertyDefinitions()) {
			RXTypeView propertyView = RXTypeView.createView(getActivity(), propDef.getName(), propDef.getType());
			propertyView.setRequired(propDef.isRequired());
			// propertyView.setEnabled(propDef.getDependsOn().size() == 0);
			propertyView.setOnValueChangedListener(new OnValueChangedListener() {

				@Override
				public void onValueChanged(String name, Object value) {
					// reaction.addParam()
					reflex.getReaction().setParam(new RXReactionProperty(name, value));

					updateProperties();
				}
			});

			propertyViews.add(propertyView);
			propertiesLayout.addView(propertyView,
					new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		}

	}

	private void updateProperties() {

	}
}
