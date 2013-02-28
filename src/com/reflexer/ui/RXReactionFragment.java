
package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;

import com.reflexer.R;
import com.reflexer.model.RXConditionDefinition;
import com.reflexer.model.RXPropertyDefinition;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.ui.adapters.RXStimuliAdapter;
import com.reflexer.ui.views.RXTypeView;
import com.reflexer.ui.views.RXTypeView.OnValueChangedListener;

import org.apache.http.impl.conn.ProxySelectorRoutePlanner;

import java.io.IOException;
import java.util.ArrayList;

public class RXReactionFragment extends Fragment {

    ArrayList<String> list = new ArrayList<String>();
    RXReaction reaction;
    private ArrayList<RXReactionDefinition> reactions;
    private LinearLayout conditionsLayout;
    private final ArrayList<RXTypeView> conditionViews = new ArrayList<RXTypeView>();

    public static RXReactionFragment newInstance() {
        RXReactionFragment fragment = new RXReactionFragment();

        return fragment;
    }

    public static RXReactionFragment newInstance(RXReaction reaction) {
        RXReactionFragment fragment = new RXReactionFragment(reaction);

        return fragment;
    }

    public RXReactionFragment(RXReaction reaction) {
        this.reaction = reaction;
        showConditions();
    }

    public RXReactionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reaction, null);

        getReactionDefinitions();

        setupReactionPicker(view);

        conditionsLayout = (LinearLayout)view.findViewById(R.id.conditions_layout);

        if (reaction == null) {
            initReactionFromDefinitions(0);
        }

        return view;
    }

    private void setupReactionPicker(View rootView) {
        ImageView leftReactionPicker = (ImageView)rootView.findViewById(R.id.stimuli_picker_left);
        ImageView rightReactionPicker = (ImageView)rootView.findViewById(R.id.stimuli_picker_right);

        leftReactionPicker.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        rightReactionPicker.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getReactionDefinitions() {

        try {
            reactions = RXReaction.getReactionDefinitions(getActivity());
            for (RXReactionDefinition reaction : reactions) {
                list.add(reaction.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initReactionFromDefinitions(int index) {
        this.reaction = RXReaction.createReaction(getActivity(), reactions.get(index).getName());
        showConditions();
    }

    private void showConditions() {

        conditionViews.clear();
        conditionsLayout.removeAllViews();

        for (RXPropertyDefinition propDef : reaction.getDefinition().getPropertyDefinitions()) {
            RXTypeView propertyView = RXTypeView.createView(getActivity(), propDef.getName(), propDef.getType());
            propertyView.setRequired(propDef.isRequired());
            // propertyView.setEnabled(propDef.getDependsOn().size() == 0);
            propertyView.setOnValueChangedListener(new OnValueChangedListener() {

                @Override
                public void onValueChanged(String name, Object value) {
                    // reaction.addParam()

                    updateConditions();
                }
            });

            conditionViews.add(propertyView);
            conditionViews.add(propertyView);
            conditionsLayout.addView(propertyView,
                    new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            Log.d("showConditions", "added: " + propDef.getName());

        }

    }

    private void updateConditions() {
        for (RXTypeView typeView : conditionViews) {
            RXPropertyDefinition propDef = reaction.getDefinition().getPropertyDefinitionByName(typeView.getName());

            typeView.setEnabled(true);

        }

    }
}
