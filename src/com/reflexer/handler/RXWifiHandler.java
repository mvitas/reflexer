package com.reflexer.handler;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

public class RXWifiHandler extends RXHandler {

	private boolean connected;

	private String currentNetwork;

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
			// int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
			// WifiManager.WIFI_STATE_UNKNOWN);

			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();

			Log.d("Receiver", "network info: " + netInfo);
			Log.d("Receiver", "wifi info: " + wifiInfo);
		} else if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
			boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);

			Log.d("Receiver", "network connected: " + connected);
		}
	}
}
