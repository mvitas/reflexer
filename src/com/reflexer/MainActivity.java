package com.reflexer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.model.RXReactionProperty;
import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliProperty;
import com.reflexer.model.reaction.RXWifiReaction;
import com.reflexer.util.RXUtil;

public class MainActivity extends Activity {

	private List<WifiConfiguration> configuredNetworks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// WifiManager wifiManager =
		// (WifiManager)getSystemService(Context.WIFI_SERVICE);
		// configuredNetworks = wifiManager.getConfiguredNetworks();
		//
		// Log.d("Reflexer", "configured networks: " + configuredNetworks);

		RXDatabaseHelper helper = new RXDatabaseHelper(this, "reflexer", null,
				1);
		SQLiteDatabase writeableDatabase = helper.getWritableDatabase();

		Cursor cur = writeableDatabase.rawQuery(
				"SELECT * FROM rx_stimuli_property;", null);
		cur.moveToFirst();

		Cursor cur3 = writeableDatabase.rawQuery("SELECT * FROM rx_reflex;",
				null);
		cur3.moveToFirst();

		Cursor cur4 = writeableDatabase.rawQuery("SELECT * FROM rx_stimuli;",
				null);
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

		RXStimuli stimuli = new RXStimuli("wifi connectivity change");

		RXStimuliProperty s1 = new RXStimuliProperty("connected", "on");
		RXStimuliProperty s2 = new RXStimuliProperty("beautiful", "vitas");

		ArrayList<RXStimuliProperty> stimuliPropertyList = new ArrayList<RXStimuliProperty>();

		stimuliPropertyList.add(s1);
		stimuliPropertyList.add(s2);

		stimuli.setParamsList(stimuliPropertyList);
		// END STIMULI

		// REACTION
		// ovo nece biti wifi reaction
		RXWifiReaction wifiReaction = new RXWifiReaction(
				"email ili odmah tu napisat com.android.intent.ACTION_SEND");

		RXReactionProperty p1 = new RXReactionProperty("to",
				"[\"mv@gmail.com\"]");
		RXReactionProperty p2 = new RXReactionProperty("cc",
				"[\"ik@gmail.com\", \"dp@gmail.com\"]");
		RXReactionProperty p3 = new RXReactionProperty("title",
				"xyz naslov je je");

		wifiReaction.addRXParam(p1);
		wifiReaction.addRXParam(p2);
		wifiReaction.addRXParam(p3);

		// END REACTION

		// REFLEX

		RXReflex reflex = new RXReflex("reflexcina", stimuli, wifiReaction);

		// END REFLEX

		int id = helper.insertRxReflex(writeableDatabase, reflex);
		Log.d(RXUtil.APP_TAG, "reflex inserted: id value equals --- " + id);

		// Fetch the just inserted reflex
		Cursor rawQuery = helper.queryALLReflexs(writeableDatabase);

		if (rawQuery != null) {
			while (rawQuery.moveToNext()) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i<12; i++){
					if (i<4){
						sb.append("REFLEX ").append(rawQuery.getString(i)).append("\n");
					} else if (i>=4 && i < 7){
						sb.append("STIMULI PROPERTY ").append(rawQuery.getString(i)).append("\n");
					} else if (i == 7){
						sb.append("STIMULI ").append(rawQuery.getString(i)).append("\n");
					} else if (i> 7 && i < 11){
						sb.append("RXPROPERTY ").append(rawQuery.getString(i)).append("\n");
					} else {
						sb.append("REACTION ").append(rawQuery.getString(i)).append("\n");
					}
				}
				Log.d(RXUtil.APP_TAG, sb.toString() );
				Log.d(RXUtil.APP_TAG, "\n \n \n new elem");
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
