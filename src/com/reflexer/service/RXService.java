package com.reflexer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.reflexer.config.RXActions;
import com.reflexer.handler.RXHandler;
import com.reflexer.model.RXReactionDefinition;
import com.reflexer.model.RXReflex;
import com.reflexer.receiver.RXReceiver;

import java.util.ArrayList;

//TODO: start the service on boot
public class RXService extends Service {

	private static final String SLASH = "/";

	/**
	 * List of all available reactions.
	 */
	private ArrayList<RXReactionDefinition> reactionList;

	/**
	 * List containing all the reflexes.
	 */
	private ArrayList<RXReflex> reflexes;

	/**
	 * List containing currently active handlers. Accessed from multiple
	 * threads: BroadcastReceiver thread and threads spawned by RXReceiver.
	 */
	private volatile ArrayList<RXHandler> handlers;

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
	}

	/**
	 * Adds a new reflex. Reflex is by default active when added.
	 * 
	 * @param reflex
	 */
	public void addReflex(RXReflex reflex) {
		reflexes.add(reflex);
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

	public ArrayList<RXHandler> getHandlers() {
		return handlers;
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
		registerBroadcastReceiver();

		// TODO restore reflexes from database
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	private void registerBroadcastReceiver() {
		receiver = new RXReceiver(this);
		registerReceiver(receiver, RXActions.getIntentFilter());
	}

	private void unregisterBroadcastReceiver() {
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
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
