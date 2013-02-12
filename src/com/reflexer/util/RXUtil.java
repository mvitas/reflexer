package com.reflexer.util;

import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

public class RXUtil {

	/**
	 * Dohvaca konfigurirane WiFi mreze u Settingsima. Vraca praznu listu ako je WiFi iskljucen. 
	 * 
	 * @param context
	 * @return
	 */
	public final static List<WifiConfiguration> getConfiguredWifiNetworks(Context context) {
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConfiguredNetworks();
	}
}
