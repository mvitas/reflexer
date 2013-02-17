package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;

import com.reflexer.model.RXStimuli;

import java.util.ArrayList;

public abstract class RXHandler {

	/**
	 * List of RXStimuli extended classes that are associated with this handler.
	 */
	protected ArrayList<RXStimuli> associatedStimuli;

	public void associateStimuli(RXStimuli stimuli) {
		associatedStimuli.add(stimuli);
	}

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
	public abstract void onReceive(Context context, Intent intent);

	public void removeAssociation(RXStimuli stimuli) {
		associatedStimuli.remove(stimuli);
	}
}
