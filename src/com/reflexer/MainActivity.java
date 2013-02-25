package com.reflexer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.model.reaction.RXTestReaction;
import com.reflexer.service.RXBinder;
import com.reflexer.service.RXService;
import com.reflexer.util.RXUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private List<WifiConfiguration> configuredNetworks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent serviceIntent = new Intent(this, RXService.class);
		startService(serviceIntent);

		// WifiManager wifiManager =
		// (WifiManager)getSystemService(Context.WIFI_SERVICE);
		// configuredNetworks = wifiManager.getConfiguredNetworks();
		//
		// Log.d("Reflexer", "configured networks: " + configuredNetworks);

		RXDatabaseHelper helper = new RXDatabaseHelper(this, "reflexer", null, 1);
		SQLiteDatabase writeableDatabase = helper.getWritableDatabase();

		Cursor cur = writeableDatabase.rawQuery("SELECT * FROM rx_stimuli_property;", null);
		cur.moveToFirst();

		Cursor cur3 = writeableDatabase.rawQuery("SELECT * FROM rx_reflex;", null);
		cur3.moveToFirst();

		Cursor cur4 = writeableDatabase.rawQuery("SELECT * FROM rx_stimuli;", null);
		cur4.moveToFirst();

		// try {
		// writeableDatabase.beginTransaction();
		//
		// writeableDatabase.execSQL("INSERT INTO rx_reaction (rx_reaction_name) VALUES ('com.android.screen.Brightness.kaoSpika')");
		//
		// Cursor cur5 =
		// writeableDatabase.rawQuery("SELECT * FROM rx_reaction;", null);
		//
		// while (cur5.moveToNext()){
		// String s = cur5.getString(0);
		// s.toString();
		// }
		//
		// //TODO umjesto last_insert_rowid() dohvatiti id bas rx_reactiona jer
		// bi se inace moglo dogoditi da kada se obrise neki row da se sve
		// pojebe - mozda
		// writeableDatabase.execSQL("INSERT INTO rx_property (rx_property_name, rx_property_value, fk_rx_reaction) VALUES ('test name', 'test value', last_insert_rowid())");
		//
		// Cursor cur2 =
		// writeableDatabase.rawQuery("SELECT * FROM rx_property;", null);
		//
		// while (cur2.moveToNext() ){
		// String s = cur2.getString(0);
		// s.toString();
		// }
		// // writeableDatabase.setTransactionSuccessful();
		// } catch (Exception e ){
		// Log.d("REFLEXR", e.toString());
		// } finally {
		// writeableDatabase.endTransaction();
		// }

		// STIMULI

		RXStimuli stimuli = new RXStimuli(this, "WiFiStimuli");

		RXStimuliCondition s1 = new RXStimuliCondition("connected", "on");
		RXStimuliCondition s2 = new RXStimuliCondition("beautiful", "vitas");

		ArrayList<RXStimuliCondition> stimuliConditionList = new ArrayList<RXStimuliCondition>();

		stimuliConditionList.add(s1);
		stimuliConditionList.add(s2);

		stimuli.setConditionList(stimuliConditionList);
		// END STIMULI

		// REACTION
		// ovo nece biti wifi reaction
		RXTestReaction wifiReaction = (RXTestReaction) RXReaction.createReaction(this, "TestReaction");

		RXReactionProperty p1 = new RXReactionProperty("to", "[\"mv@gmail.com\"]");
		RXReactionProperty p2 = new RXReactionProperty("cc", "[\"ik@gmail.com\", \"dp@gmail.com\"]");
		RXReactionProperty p3 = new RXReactionProperty("title", "xyz naslov je je");

		wifiReaction.addParam(p1);
		wifiReaction.addParam(p2);
		wifiReaction.addParam(p3);

		// END REACTION

		// REFLEX

		RXReflex reflex = new RXReflex(stimuli, wifiReaction); // new
																// RXReflex(this,
																// "reflexcina",
																// stimuli,
																// wifiReaction);

		// END REFLEX

		int id = helper.insertRxReflex(writeableDatabase, reflex);
		Log.d(RXUtil.APP_TAG, "reflex inserted: id value equals --- " + id);

		// Fetch the just inserted reflex
		Cursor rawQuery = helper.queryALLReflexs(writeableDatabase);

		if (rawQuery != null) {
			while (rawQuery.moveToNext()) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < 12; i++) {
					if (i < 4) {
						sb.append("REFLEX ").append(rawQuery.getString(i)).append("\n");
					} else if (i >= 4 && i < 7) {
						sb.append("STIMULI PROPERTY ").append(rawQuery.getString(i)).append("\n");
					} else if (i == 7) {
						sb.append("STIMULI ").append(rawQuery.getString(i)).append("\n");
					} else if (i > 7 && i < 11) {
						sb.append("RXPROPERTY ").append(rawQuery.getString(i)).append("\n");
					} else {
						sb.append("REACTION ").append(rawQuery.getString(i)).append("\n");
					}
				}
				Log.d(RXUtil.APP_TAG, sb.toString());
				Log.d(RXUtil.APP_TAG, "\n \n \n new elem");
			}
		}

	}

	private RXBinder serviceBinder;

	private final ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			Log.d("Reflexer", "onServiceConnected");
			serviceBinder = ((RXBinder) binder);

			RXStimuli stimuli = new RXStimuli(MainActivity.this, "WiFiStimuli");
			stimuli.setCondition(new RXStimuliCondition("connected", Boolean.TRUE));

			RXReaction reaction = RXReaction.createReaction(MainActivity.this, "TestReaction");
			reaction.addParam(new RXReactionProperty("output", "testing output from TestReaction"));

			RXReflex reflex = new RXReflex(stimuli, reaction);

			serviceBinder.addReflex(reflex);
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			serviceBinder = null;
		}
	};

	@Override
	protected void onResume() {
		super.onResume();

		bindService(new Intent(this, RXService.class), mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause() {
		super.onPause();

		unbindService(mConnection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
