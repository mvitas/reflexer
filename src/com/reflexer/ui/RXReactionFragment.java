package com.reflexer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reflexer.R;

public class RXReactionFragment extends Fragment {

	public static RXReactionFragment newInstance() {
		RXReactionFragment fragment = new RXReactionFragment();

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.activity_splash, null);
	}

}
