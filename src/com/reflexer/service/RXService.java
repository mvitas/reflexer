package com.reflexer.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.reflexer.database.RXDatabaseHelper;
import com.reflexer.database.mapper.RXMapper;
import com.reflexer.handler.RXHandler;
import com.reflexer.model.RXReflex;
import com.reflexer.receiver.RXReceiver;

import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;

//TODO: start the service on boot
public class RXService extends Service {

	/**
	 * List containing all the reflexes.
	 */
	private ArrayList<RXReflex> reflexes;

	/**
	 * BroadcastReceiver that intercepts all possible actions.
	 */
	private RXReceiver receiver;

	/**
	 * Activates an existing reflex. If a reflex is already active has no
	 * effect.
	 * 
	 * @param reflex
	 */
	public void activateReflex(RXReflex reflex) {
		reflex.getStimuli().getDefinition().getHandler().addObserver(reflex.getStimuli());
		reflex.getStimuli().setReflexListener(reflex);
		reflex.getStimuli().getDefinition().getHandler().onActivate(reflex.getStimuli());

		reRegisterBroadcastReceiver();
	}

	/**
	 * Deactivates an existing reflex. When a reflex is deactivated it is still
	 * stored in the database but it will not react to stimuli.
	 * 
	 * @param reflex
	 */
	public void deactivateReflex(RXReflex reflex) {
		reflex.getStimuli().getDefinition().getHandler().removeObserver(reflex.getStimuli());
		reflex.getStimuli().setReflexListener(null);
		reflex.getStimuli().getDefinition().getHandler().onDeactivate(reflex.getStimuli());

		reRegisterBroadcastReceiver();
	}

	/**
	 * Adds a new reflex. Reflex is by default active when added.
	 * 
	 * @param reflex
	 */
	public void addReflex(RXReflex reflex) {
		reflexes.add(reflex);
		RXDatabaseHelper helper = new RXDatabaseHelper(this, null, null, 1);
		
		SQLiteDatabase writableDatabase = helper.getWritableDatabase();
		helper.insertRxReflex(writableDatabase, reflex, this);
		writableDatabase.close();
		activateReflex(reflex);
	}

	/**
	 * Removes an existing reflex. Reflex is permanently deleted.
	 * 
	 * @param reflex
	 */
	public void removeReflex(RXReflex reflex) {
		deactivateReflex(reflex);
		reflexes.remove(reflex);
	}

	/**
	 * Returns a list of stored reflexes.
	 * 
	 * 
	 * @return
	 */
	public ArrayList<RXReflex> getReflexes() {
		return reflexes;
	}

	@Override
	public IBinder onBind(Intent intent) {
		RXBinder binder = new RXBinder(this);
		return binder;
	}

	/**
	 * 
	 * Entry point - register receiver and initiaize it to listen to all acitons
	 * set in database. Initialize Handlers for specified acitons and register
	 * them as observers for the RXService
	 * 
	 * 
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		RXDatabaseHelper helper = new RXDatabaseHelper(this, null, null, 1);
		SQLiteDatabase readableDatabase = helper.getReadableDatabase();
		
		try {
			reflexes = RXMapper.getAllReflexes(this, helper.queryALLReflexs(readableDatabase));
			for (RXReflex r : reflexes){
				activateReflex(r);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		registerBroadcastReceiver();

		// TODO restore reflexes from database
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	/**
	 * Creates a new IntentFilter that contains all the actions that are used by
	 * currently active reflexes.
	 * 
	 * @return
	 */
	private IntentFilter generateIntentFilter() {
		IntentFilter filter = new IntentFilter();
		Set<String> actions = new HashSet<String>();

		for (RXReflex reflex : reflexes) {
			RXHandler handler = reflex.getStimuli().getDefinition().getHandler();
			if (handler.hasObservers()) {
				actions.addAll(handler.getInterestingActionsList());
			}
		}

		for (String action : actions) {
			Log.d("RXService", "adding action: " + action);
			filter.addAction(action);
		}

		return filter;
	}

	private void registerBroadcastReceiver() {
		receiver = new RXReceiver(this);

		registerReceiver(receiver, generateIntentFilter());
	}

	private void unregisterBroadcastReceiver() {
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}

	private void reRegisterBroadcastReceiver() {
		unregisterBroadcastReceiver();
		registerBroadcastReceiver();
	}

	/**
	 * Unregister receiver and destroy handlers
	 * 
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterBroadcastReceiver();
	}

}
