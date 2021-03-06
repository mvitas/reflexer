package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class RXWifiHandler extends RXHandler {

	private static final String CONDITION_NETWORK_NAME = "network-name";
	private static final String CONDITION_CONNECTED = "connected";

	private static final String NETWORK_STATE_CHANGED_ACTION = "network-state-changed";
	private static final String SUPPLICANT_CONNECTION_CHANGE_ACTION = "supplicant-connection-change";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("RXWifiHandler", "onReceive( " + intent + " )");
		if (isAction(NETWORK_STATE_CHANGED_ACTION, intent)) {
			Log.d("RXWifiHandler", "NETWORK_STATE_CHANGED_ACTION");
			// NetworkInfo netInfo =
			// intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			notifyConditionState(CONDITION_NETWORK_NAME, wifiInfo.getSSID());
		} else if (isAction(SUPPLICANT_CONNECTION_CHANGE_ACTION, intent)) {
			Log.d("RXWifiHandler", "SUPPLICANT_CONNECTION_CHANGE_ACTION");
			boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);

			notifyConditionState(CONDITION_CONNECTED, Boolean.valueOf(connected));
		}
	}
}
