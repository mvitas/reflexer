package com.reflexer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

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
import android.widget.TextView;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.database.mapper.RXMapper;
import com.reflexer.model.RXReaction;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliCondition;
import com.reflexer.model.reaction.RXDialogReaction;
import com.reflexer.service.RXBinder;
import com.reflexer.service.RXService;
import com.reflexer.util.RXTypeCaster;
import com.reflexer.util.RXUtil;

public class MainActivity extends Activity {

	private List<WifiConfiguration> configuredNetworks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView labela = (TextView) findViewById(R.id.labela);

		Intent serviceIntent = new Intent(this, RXService.class);
		startService(serviceIntent);

//		// WifiManager wifiManager =
//		// (WifiManager)getSystemService(Context.WIFI_SERVICE);
//		// configuredNetworks = wifiManager.getConfiguredNetworks();
//		//
//		// Log.d("Reflexer", "configured networks: " + configuredNetworks);
//
//		RXDatabaseHelper helper = new RXDatabaseHelper(this, "reflexer", null,
//				1);
//		SQLiteDatabase writeableDatabase = helper.getWritableDatabase();
//
//		Cursor cur = writeableDatabase.rawQuery(
//				"SELECT * FROM rx_stimuli_property;", null);
//		cur.moveToFirst();
//
//		Cursor cur3 = writeableDatabase.rawQuery("SELECT * FROM rx_reflex;",
//				null);
//		cur3.moveToFirst();
//
//		Cursor cur4 = writeableDatabase.rawQuery("SELECT * FROM rx_stimuli;",
//				null);
//		cur4.moveToFirst();
//
//		// try {
//		// writeableDatabase.beginTransaction();
//		//
//		// writeableDatabase.execSQL("INSERT INTO rx_reaction (rx_reaction_name) VALUES ('com.android.screen.Brightness.kaoSpika')");
//		//
//		// Cursor cur5 =
//		// writeableDatabase.rawQuery("SELECT * FROM rx_reaction;", null);
//		//
//		// while (cur5.moveToNext()){
//		// String s = cur5.getString(0);
//		// s.toString();
//		// }
//		//
//		// //TODO umjesto last_insert_rowid() dohvatiti id bas rx_reactiona jer
//		// bi se inace moglo dogoditi da kada se obrise neki row da se sve
//		// pojebe - mozda
//		// writeableDatabase.execSQL("INSERT INTO rx_property (rx_property_name, rx_property_value, fk_rx_reaction) VALUES ('test name', 'test value', last_insert_rowid())");
//		//
//		// Cursor cur2 =
//		// writeableDatabase.rawQuery("SELECT * FROM rx_property;", null);
//		//
//		// while (cur2.moveToNext() ){
//		// String s = cur2.getString(0);
//		// s.toString();
//		// }
//		// // writeableDatabase.setTransactionSuccessful();
//		// } catch (Exception e ){
//		// Log.d("REFLEXR", e.toString());
//		// } finally {
//		// writeableDatabase.endTransaction();
//		// }
//
//		// STIMULI
//
//		RXStimuli stimuli = new RXStimuli(this, "WiFiStimuli");
//
//		int type;
//		int type2;
//
//		try {
//			type = stimuli.getStimuliConditionType(this, "WiFiStimuli",
//					"connected");
//			type2 = stimuli.getStimuliConditionType(this, "WiFiStimuli",
//					"network-name");
//		} catch (Exception e) {
//			// TODO:
//			// database integrity fucked up and type was not inserted properly
//			return;
//		}
//
//		RXStimuliCondition s1;
//		RXStimuliCondition s2;
//		try {
//			s1 = new RXStimuliCondition("connected", RXTypeCaster.getInstance()
//					.initializeType(type, "[\"true\"]"));
//			s2 = new RXStimuliCondition("network-name", RXTypeCaster
//					.getInstance().initializeType(type2, "[\"preduzece\"]"));
//		} catch (JSONException e) {
//			// TODO: handleaj ovo greska iz baze
//			e.printStackTrace();
//			return;
//		}
//
//		ArrayList<RXStimuliCondition> stimuliConditionList = new ArrayList<RXStimuliCondition>();
//
//		stimuliConditionList.add(s1);
//		stimuliConditionList.add(s2);
//
//		stimuli.setConditionList(stimuliConditionList);
//		// END STIMULI
//
//		// REACTION
//		// ovo nece biti wifi reaction
//		RXDialogReaction rxDialogReaction = (RXDialogReaction) RXReaction
//				.createReaction(this, "DialogReaction");
//
//		
//		int type1Rea;
//		int type2Rea;
//
//		try {
//			type1Rea = RXReaction.getReactionPropertyType(this, "DialogReaction",
//					"title");
//			type2Rea = RXReaction.getReactionPropertyType(this, "DialogReaction",
//					"text");
//		} catch (Exception e) {
//			throw new IllegalStateException("reaction property init failed");
//		}
//		
//		RXReactionProperty p1;
//		RXReactionProperty p2;
//		try {
//			p1 = new RXReactionProperty("title",
//					RXTypeCaster.getInstance().initializeType(type1Rea, "[\"penisici i pice\"]"));
//			p2 = new RXReactionProperty("text",
//					RXTypeCaster.getInstance().initializeType(type2Rea, "[\"tesits text to testis\"]"));
//		} catch (JSONException e1) {
//			throw new IllegalStateException("reaction property init failed");
//		}
//		
//		rxDialogReaction.addParam(p1);
//		rxDialogReaction.addParam(p2);
//
//		// END REACTION
//
//		// REFLEX
//
//		RXReflex reflex = new RXReflex("first reflex", stimuli,
//				rxDialogReaction); // new
//		// RXReflex(this,
//		// "reflexcina",
//		// stimuli,
//		// wifiReaction);
//
//		// END REFLEX
//
//		int id = helper.insertRxReflex(writeableDatabase, reflex, this);
//
//		Log.d(RXUtil.APP_TAG, "reflex inserted: id value equals --- " + id);
//
//		// Fetch the just inserted reflex
//		Cursor rawQuery = helper.queryALLReflexs(writeableDatabase);
//
//		if (rawQuery != null) {
//			while (rawQuery.moveToNext()) {
//				StringBuilder sb = new StringBuilder();
//				for (int i = 0; i < 12; i++) {
//					if (i < 4) {
//						sb.append("REFLEX ").append(rawQuery.getString(i))
//								.append("\n");
//					} else if (i >= 4 && i < 7) {
//						sb.append("STIMULI PROPERTY ")
//								.append(rawQuery.getString(i)).append("\n");
//					} else if (i == 7) {
//						sb.append("STIMULI ").append(rawQuery.getString(i))
//								.append("\n");
//					} else if (i > 7 && i < 11) {
//						sb.append("RXPROPERTY ").append(rawQuery.getString(i))
//								.append("\n");
//					} else {
//						sb.append("REACTION ").append(rawQuery.getString(i))
//								.append("\n");
//					}
//				}
//				Log.d(RXUtil.APP_TAG, sb.toString());
//				Log.d(RXUtil.APP_TAG, "\n \n \n new elem");
//			}
//		}
//
//		rawQuery.moveToPosition(-1);
//
//		ArrayList<RXReflex> allReflexes = null;
//		try {
//			allReflexes = RXMapper.getAllReflexes(this, rawQuery);
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		if (allReflexes != null)
//			Log.d(RXUtil.APP_TAG, allReflexes.size() + " sizezezezeze");

	}

	private RXBinder serviceBinder;

	private final ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			Log.d("Reflexer", "onServiceConnected");
			serviceBinder = ((RXBinder) binder);

			RXStimuli stimuli = new RXStimuli(MainActivity.this, "WiFiStimuli");
			stimuli.setCondition(new RXStimuliCondition("connected", Boolean.TRUE));
			stimuli.setCondition(new RXStimuliCondition("network-name", "AlbisWLAN"));

			RXReaction reaction = RXReaction.createReaction(MainActivity.this,
					"TestReaction");
			reaction.addParam(new RXReactionProperty("output",
					"testing output from TestReaction"));

			RXReflex reflex = new RXReflex("second reflex", stimuli, reaction);

			RXStimuli batStim = new RXStimuli(MainActivity.this, "BatteryLevelStimuli");
			batStim.setCondition(new RXStimuliCondition("level", 50));

			RXReaction batRe = RXReaction.createReaction(MainActivity.this, "TestReaction");
			batRe.addParam(new RXReactionProperty("output", "testing battery level stimuli"));

			RXReflex batRef = new RXReflex("xy testis", batStim, batRe);

			RXStimuli pwStim = new RXStimuli(MainActivity.this, "PowerConnectedStimuli");
			pwStim.setCondition(new RXStimuliCondition("power-connected", true));

			RXReaction pwRe = RXReaction.createReaction(MainActivity.this, "TestReaction");
			pwRe.addParam(new RXReactionProperty("output", "testing power connected stimuli"));

			RXReflex pwRef = new RXReflex("bla bla bla sad cemo jest?",pwStim, pwRe);

			serviceBinder.addReflex(reflex);
			serviceBinder.addReflex(batRef);
			serviceBinder.addReflex(pwRef);
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			serviceBinder = null;
		}
	};

	@Override
	protected void onResume() {
		super.onResume();

		bindService(new Intent(this, RXService.class), mConnection,
				Context.BIND_AUTO_CREATE);
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
