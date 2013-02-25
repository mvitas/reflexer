package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.reflexer.model.RXStimuli;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RXHandler {

	/**
	 * List of RXStimuli classes that are associated with this handler.
	 */
	protected ArrayList<RXStimuli> observers = new ArrayList<RXStimuli>();

	/**
	 * List of actions that RXHandler implementation is listening to.
	 */
	protected HashMap<String, String> interestingActions = new HashMap<String, String>();

	public void addObserver(RXStimuli stimuli) {
		observers.add(stimuli);
	}

	public void removeObserver(RXStimuli stimuli) {
		observers.remove(stimuli);
	}

	public boolean hasObservers() {
		return observers.size() > 0;
	}

	public void setInterestingActions(HashMap<String, String> interestingActions) {
		this.interestingActions = interestingActions;
	}

	/**
	 * Returns a list of Intent actions that RXHandler implementation listens
	 * to.
	 * 
	 * @return ArrayList of Strings representing intent actions
	 */
	public ArrayList<String> getInterestingActionsList() {
		ArrayList<String> actionsList = new ArrayList<String>();

		for (String key : interestingActions.keySet()) {
			actionsList.add(interestingActions.get(key));
		}

		return actionsList;
	}

	public boolean isInterestingAction(String action) {
		ArrayList<String> interestingActions = getInterestingActionsList();

		for (int i = 0; i < interestingActions.size(); i++) {
			if (interestingActions.get(i).equals(action)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if given Intent action equals interesting action with the given
	 * alias.
	 * 
	 * @param actionAlias
	 *            alias of the interesting action
	 * @param intent
	 *            Intent to check action
	 * @return
	 */
	public boolean isAction(String actionAlias, Intent intent) {
		String action = interestingActions.get(actionAlias);
		if (TextUtils.isEmpty(action)) {
			return false;
		}

		return action.equals(intent.getAction());
	}

	/**
	 * Notify all registered observers about the condition state change.
	 * 
	 * @param conditionName
	 *            name of the condition that has changed
	 * @param value
	 *            new value of the condition
	 */
	public void notifyConditionState(String conditionName, Object value) {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).setConditionCurrentState(conditionName, value);
		}
	}

	/**
	 * Called when a handler
	 * 
	 * @param intent
	 */
	public abstract void onReceive(Context context, Intent intent);

	/**
	 * Called when a reflex is activated. Associated stimuli is passed as
	 * argument.
	 */
	public void onActivate(RXStimuli stimuli) {

	}

	/**
	 * Called when a reflex is deactivated. Associated stimuli is passed as
	 * argument.
	 */
	public void onDeactivate(RXStimuli stimuli) {

	}
}
