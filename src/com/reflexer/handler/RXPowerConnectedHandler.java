package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RXPowerConnectedHandler extends RXHandler {

	private static final String CONDITION_POWER_CONNECTED = "power-connected";

	private static final String POWER_CONNECTED_ACTION = "power-connected";
	private static final String POWER_DISCONNECTED_ACTION = "power-disconnected";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("RxBatteryLevelHandler", "onReceive( " + intent + " )");
		if (isAction(POWER_CONNECTED_ACTION, intent)) {
			notifyConditionState(CONDITION_POWER_CONNECTED, Boolean.TRUE);
		} else if (isAction(POWER_DISCONNECTED_ACTION, intent)) {
			notifyConditionState(CONDITION_POWER_CONNECTED, Boolean.FALSE);
		}
	}

}
