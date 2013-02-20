package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.reflexer.model.RXWifiStimuli;

public class RXWifiHandler extends RXHandler {

	private final static String NETWORK_STATE_CHANGED_ACTION = "network-state-changed";
	private final static String SUPPLICANT_CONNECTION_CHANGE_ACTION = "supplicant-connection-change";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (isAction(NETWORK_STATE_CHANGED_ACTION, intent)) {
			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			for (int i = 0; i < associatedReflexes.size(); i++) {
				associatedReflexes.get(i).getRxThis()
						.setConditionState(RXWifiStimuli.CONDITION_NETWORK_NAME, wifiInfo.getSSID());
			}
		} else if (isAction(SUPPLICANT_CONNECTION_CHANGE_ACTION, intent)) {
			boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);

			for (int i = 0; i < associatedReflexes.size(); i++) {
				associatedReflexes.get(i).getRxThis()
						.setConditionState(RXWifiStimuli.CONDITION_CONNECTED, Boolean.valueOf(connected));
			}

		}
	}
}
