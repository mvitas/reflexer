
package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;

import com.reflexer.R;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.model.RXStimuli;
import com.reflexer.ui.adapters.RXStimuliAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class RXReactionFragment extends Fragment {

    ArrayList<String> list = new ArrayList<String>();
    RXReaction reaction;
    private ArrayList<RXReactionDefinition> reactions;
    private LinearLayout conditionsLayout;

    public static RXReactionFragment newInstance() {
        RXReactionFragment fragment = new RXReactionFragment();

        return fragment;
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

    }
}
