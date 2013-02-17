package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.reflexer.model.RXWifiStimuli;

import java.util.ArrayList;

public class RXWifiHandler extends RXHandler {

	// private boolean connected;

	// private String currentNetwork;

	@Override
	public ArrayList<String> getInterestingActions() {
		ArrayList<String> interestingActions = new ArrayList<String>();
		interestingActions.add(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		interestingActions.add(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);

		return interestingActions;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			for (int i = 0; i < associatedReflexes.size(); i++) {
				associatedReflexes.get(i).getRxThis()
						.setConditionState(RXWifiStimuli.CONDITION_NETWORK_NAME, wifiInfo.getSSID());
			}
		} else if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
			boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);

			for (int i = 0; i < associatedReflexes.size(); i++) {
				associatedReflexes.get(i).getRxThis()
						.setConditionState(RXWifiStimuli.CONDITION_CONNECTED, Boolean.valueOf(connected));
			}

		}
	}
}
