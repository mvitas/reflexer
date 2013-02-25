package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RXBatteryLevelHandler extends RXHandler {

	private static final String CONDITION_LEVEL = "level";

	private static final String BATTERY_CHARGED_ACTION = "battery-changed";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("RxBatteryLevelHandler", "onReceive( " + intent + " )");
		if (isAction(BATTERY_CHARGED_ACTION, intent)) {
			int level = intent.getIntExtra(CONDITION_LEVEL, 0);
			Log.d("RXBatteryLevelHandler", "level: " + level);
			notifyConditionState(CONDITION_LEVEL, Integer.valueOf(level));
		}
	}

}
