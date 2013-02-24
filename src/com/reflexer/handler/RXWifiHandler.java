package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.reflexer.model.RXStimuliProperty;

public class RXWifiHandler extends RXHandler {

	private static final String CONDITION_NETWORK_NAME = "network-name";
	private static final String CONDITION_CONNECTED = "connected";

	private static final String NETWORK_STATE_CHANGED_ACTION = "network-state-changed";
	private static final String SUPPLICANT_CONNECTION_CHANGE_ACTION = "supplicant-connection-change";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (isAction(NETWORK_STATE_CHANGED_ACTION, intent)) {
			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			for (int i = 0; i < associatedReflexes.size(); i++) {
				//ovo treba kod setconditionstate provjeravati da li vec postoji ili ne
				associatedReflexes.get(i).getRxThis().setConditionState(new RXStimuliProperty(CONDITION_NETWORK_NAME, wifiInfo.getSSID()));
			}
		} else if (isAction(SUPPLICANT_CONNECTION_CHANGE_ACTION, intent)) {
			boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);

			for (int i = 0; i < associatedReflexes.size(); i++) {
				associatedReflexes.get(i).getRxThis()
						.setConditionState(new RXStimuliProperty(CONDITION_CONNECTED, Boolean.valueOf(connected)));
			}

		}
	}
}
