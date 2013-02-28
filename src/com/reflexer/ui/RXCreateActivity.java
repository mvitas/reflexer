package com.reflexer.ui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.reflexer.R;
import com.reflexer.model.RXReflex;
import com.reflexer.service.RXBinder;
import com.reflexer.ui.adapters.RXFragmentAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class RXCreateActivity extends SherlockFragmentActivity {

	/**
	 * Value meaning that no reflex index was set.
	 */
	private static final int NO_INDEX_SET = -1;

	public static final String REFLEX_INDEX = "reflex-index";

	private ViewPager mPager;
	private TabPageIndicator mIndicator;
	private RXFragmentAdapter mAdapter;

	/**
	 * Is the reflex being updated or created.
	 */
	private boolean update;

	/**
	 * Reflex that is being updated or created
	 */
	private RXReflex reflex;

	/**
	 * IBinder interface for accessing RXService methods.
	 */
	private RXBinder serviceBinder;

	private final ServiceConnection connecton = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			serviceBinder = ((RXBinder) binder);

			setupTabs();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create);

		getSupportActionBar().setTitle("Create");

	}

	private void setupTabs() {

		int reflexIndex = getIntent().getExtras().getInt(REFLEX_INDEX, NO_INDEX_SET);

		update = reflexIndex != NO_INDEX_SET;

		if (!update) {
			createDefaultReflex();
		} else {
			getReflexWidthIndex(reflexIndex);
		}

		mAdapter = new RXFragmentAdapter(this, getSupportFragmentManager(), reflex);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

	private void createDefaultReflex() {

	}

	private void getReflexWidthIndex(int index) {

	}

}
