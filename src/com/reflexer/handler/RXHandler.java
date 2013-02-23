package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.reflexer.model.RXReflex;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RXHandler {

	/**
	 * List of RXStimuli extended classes that are associated with this handler.
	 */
	protected ArrayList<RXReflex> associatedReflexes;

	protected HashMap<String, String> interestingActions;

	public void associateReflex(RXReflex reflex) {
		associatedReflexes.add(reflex);
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
	 * Called when a handler
	 * 
	 * @param intent
	 */
	public abstract void onReceive(Context context, Intent intent);

	public void removeAssociation(RXReflex reflex) {
		associatedReflexes.remove(reflex);
	}

	public boolean hasAssociations() {
		return associatedReflexes.size() > 0;
	}
}
