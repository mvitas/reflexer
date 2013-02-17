package com.reflexer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.reflexer.model.RXReflex;

import java.util.ArrayList;

public class RXService extends Service {

	/**
	 * Activates an existing reflex. If a reflex is already active has no
	 * effect.
	 * 
	 * @param reflex
	 */
	public void activateReflex(RXReflex reflex) {

	}

	/**
	 * Adds a new reflex. Reflex is by default active when added.
	 * 
	 * @param reflex
	 */
	public void addReflex(RXReflex reflex) {

	}

	/**
	 * Deactivates an existing reflex. When a reflex is deactivated it is still
	 * stored in the database but it will not react to stimuli.
	 * 
	 * @param reflex
	 */
	public void deactivateReflex(RXReflex reflex) {

	}

	/**
	 * Returns a list of stored reflexes.
	 * 
	 * 
	 * @return
	 */
	public ArrayList<RXReflex> getReflexes() {
		return null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
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

		// final IntentFilter theFilter = new IntentFilter();
		// theFilter.addAction(ACTION);
		//
		// this.yourReceiver = new BroadcastReceiver() {
		//
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// // Do whatever you need it to do when it receives the broadcast
		// // Example show a Toast message...
		// showSuccessfulBroadcast();
		// }
		// };
		//
		// // Registers the receiver so that your service will listen for
		// broadcasts
		// this.registerReceiver(this.yourReceiver, theFilter);
	}

	/**
	 * Unregister receiver and destroy handlers
	 * 
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		// Do not forget to unregister the receiver!!!
		// this.unregisterReceiver(this.yourReceiver);
	}

	/**
	 * Removes an existing reflex. Reflex is permanently deleted.
	 * 
	 * @param reflex
	 */
	public void removeReflex(RXReflex reflex) {

	}

}
