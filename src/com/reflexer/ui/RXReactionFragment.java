
package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
    

    public static RXReactionFragment newInstance() {
        RXReactionFragment fragment = new RXReactionFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reaction, null);

        // AbstractWheel reactionWheel =
        // (AbstractWheel)view.findViewById(R.id.reaction);
        //
        // reactionWheel.setViewAdapter(new RXStimuliAdapter(getActivity()));
        // reactionWheel.setVisibleItems(7);
        // reactionWheel.addChangingListener(new OnWheelChangedListener() {
        //
        // @Override
        // public void onChanged(AbstractWheel wheel, int oldValue, int
        // newValue) {
        // Log.d("RXReactionFragment", "OnWheelChanged: " + newValue);
        // }
        // });

        try {
            ArrayList<RXReactionDefinition> reactions = RXReaction.getReactionDefinitions(getActivity());
            for (RXReactionDefinition reaction : reactions) {
                Log.d("DARIO", "reaction=" + reaction.getName());
                list.add(reaction.getName());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Spinner spinner = (Spinner)view.findViewById(R.id.reaction_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                this.stimuli = new RXStimuli(RXStimuli.getStimuliDefinitions(getActivity()).get(index));
//                RXReaction.get
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                
            }
        });

        return view;
    }

}
