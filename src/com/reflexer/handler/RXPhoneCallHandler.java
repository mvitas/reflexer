package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RXPhoneCallHandler extends RXHandler {

	private static final String CONDITION_INCOMING = "incoming";
	private static final String CONDITION_NUMBER = "number";

	private static final String INCOMING_CALL_ACTION = "incoming-call";
	private static final String OUTGOING_CALL_ACTION = "outgoing-call";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("RXPhoneCallHandler", "onReceive( " + intent + " )");
		if (isAction(INCOMING_CALL_ACTION, intent)) {
			notifyConditionState(CONDITION_INCOMING, Boolean.TRUE);
		} else if (isAction(OUTGOING_CALL_ACTION, intent)) {
			notifyConditionState(CONDITION_INCOMING, Boolean.FALSE);
		}
	}
}
