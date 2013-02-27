
package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reflexer.R;

public class RXStimuliActivity extends Fragment {

    public static RXStimuliActivity newInstance() {
        RXStimuliActivity fragment = new RXStimuliActivity();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_splash, null);
    }

}
