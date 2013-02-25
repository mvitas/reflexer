package com.reflexer.model.reaction;

import android.util.Log;

import com.reflexer.model.RXReaction;

public class RXTestReaction extends RXReaction {

	@Override
	public void execute() {
		if (getParamByName("output") != null) {
			String output = (String) getParamByName("output").getValue();
			Log.d("RXTestReaction", output);
		} else {
			Log.d("RXTestReaction", "execute()");
		}
	}

}
