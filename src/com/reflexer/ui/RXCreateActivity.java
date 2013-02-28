package com.reflexer.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.reflexer.R;
import com.reflexer.model.RXConditionDefinition;
import com.reflexer.model.RXPropertyDefinition;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.service.RXBinder;
import com.reflexer.service.RXService;
import com.reflexer.ui.adapters.RXFragmentAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.io.IOException;

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

	private final ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			serviceBinder = ((RXBinder) binder);

			int reflexIndex = NO_INDEX_SET;
			if (getIntent().getExtras() != null) {
				reflexIndex = getIntent().getExtras().getInt(REFLEX_INDEX, NO_INDEX_SET);
			}

			update = reflexIndex != NO_INDEX_SET;

			if (!update) {
				reflex = createDefaultReflex();
			} else {
				reflex = getReflexWidthIndex(reflexIndex);
			}

			mAdapter.setReflex(reflex);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

		getSupportActionBar().setTitle("Create");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mAdapter = new RXFragmentAdapter(this, getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
	}

	@Override
	protected void onPause() {
		super.onPause();

		unbindService(connection);
	}

	@Override
	protected void onResume() {
		super.onResume();

		bindService(new Intent(this, RXService.class), connection, Context.BIND_AUTO_CREATE);

	};

	private RXStimuli createDefaultStimuli() {
		try {
			return new RXStimuli(RXStimuli.getStimuliDefinitions(this).get(0));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private RXReaction createDefaultReaction() {
		try {
			return RXReaction.createReaction(RXReaction.getReactionDefinitions(this).get(0));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private RXReflex createDefaultReflex() {
		return new RXReflex("", createDefaultStimuli(), createDefaultReaction());
	}

	private RXReflex getReflexWidthIndex(int index) {
		return serviceBinder.getReflexes().get(index);
	}

	private boolean verifyReflex() {
		for (RXConditionDefinition def : reflex.getStimuli().getDefinition().getConditionDefinitons()) {
			if (def.isRequired() && reflex.getStimuli().getConditionByName(def.getName()).getValue() != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Fill all required properties");
				builder.setMessage("Stimuli condition " + def.getName() + " is required and must be filled");
				builder.setPositiveButton("Ok", null);

				builder.create().show();

				return false;
			}
		}

		for (RXPropertyDefinition def : reflex.getReaction().getDefinition().getPropertyDefinitions()) {
			if (def.isRequired() && reflex.getReaction().getRXParamByName(def.getName()).getValue() == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Fill all required properties");
				builder.setMessage("Reaction property " + def.getName() + " is required and must be filled");
				builder.setPositiveButton("Ok", null);

				builder.create().show();

				return false;
			}
		}

		return true;
	}

	private void addOrUpdateReflex() {
		if (verifyReflex()) {
			serviceBinder.addReflex(reflex);
		}
	}

	private void deleteReflex() {
		serviceBinder.removeReflex(reflex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Create reflex").setIcon(R.drawable.action_accept).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		if (update) {
			menu.add("Delete reflex").setIcon(R.drawable.action_discard)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getTitle().equals("Create reflex")) {
			addOrUpdateReflex();
		} else if (item.getTitle().equals("Delete reflex")) {
			deleteReflex();
		}
		// Intent intent = new Intent(this, RXCreateActivity.class);
		// startActivity(intent);

		return super.onMenuItemSelected(featureId, item);
	}

}
