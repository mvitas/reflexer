package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;

import com.reflexer.model.RXReflex;
import com.reflexer.model.RXStimuli;

import java.util.ArrayList;

public abstract class RXHandler {

	/**
	 * List of RXStimuli extended classes that are associated with this handler.
	 */
	protected ArrayList<RXReflex> associatedReflexes;

	public void associateReflex(RXReflex reflex) {
		associatedReflexes.add(reflex);
	}

	/**
	 * Returns a list of Intent actions that RXHandler implementation listens
	 * to.
	 * 
	 * @return ArrayList of Strings representing intent actions
	 */
	public abstract ArrayList<String> getInterestingActions();

	public boolean isInterestingAction(String action) {
		ArrayList<String> interestingActions = getInterestingActions();

		for (int i = 0; i < interestingActions.size(); i++) {
			if (interestingActions.get(i).equals(action)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Called when a handler
	 * 
	 * @param intent
	 */
	public abstract void onReceive(Context context, Intent intent);

	public void removeAssociation(RXStimuli stimuli) {
		associatedReflexes.remove(stimuli);
	}
}
