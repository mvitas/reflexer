package com.reflexer.handler;

import android.content.Intent;

import java.util.ArrayList;

public abstract class RXHandler {

	/**
	 * Returns a list of Intent actions that RXHandler implementation listens
	 * to.
	 * 
	 * @return ArrayList of Strings representing intent actions
	 */
	public abstract ArrayList<String> getInterestingActions();

	/**
	 * Called when a handler
	 * 
	 * @param intent
	 */
	public abstract void onReceive(Intent intent);
}
