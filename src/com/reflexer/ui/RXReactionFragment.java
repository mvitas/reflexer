
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
import com.reflexer.ui.adapters.RXStimuliAdapter;

public class RXReactionFragment extends Fragment {

    public static RXReactionFragment newInstance() {
        RXReactionFragment fragment = new RXReactionFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reaction, null);

        AbstractWheel reactionWheel = (AbstractWheel)view.findViewById(R.id.reaction);

        reactionWheel.setViewAdapter(new RXStimuliAdapter(getActivity()));
        reactionWheel.setVisibleItems(7);
        reactionWheel.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                Log.d("RXReactionFragment", "OnWheelChanged: " + newValue);
            }
        });

        return view;
    }

}
