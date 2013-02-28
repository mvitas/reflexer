package com.reflexer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.reflexer.R;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.ui.adapters.RXFragmentAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.io.IOException;
import java.util.ArrayList;

public class RXCreateActivity extends SherlockFragmentActivity {

	public static final String REFLEX_INDEX = "reflex-index";

	private ViewPager mPager;
	private TabPageIndicator mIndicator;
	private RXFragmentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create);

		getSupportActionBar().setTitle("Create");

		mAdapter = new RXFragmentAdapter(this, getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

	}

}
