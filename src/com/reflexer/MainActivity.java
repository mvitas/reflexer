package com.reflexer;

import java.util.List;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    private List<WifiConfiguration> configuredNetworks;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        configuredNetworks = wifiManager.getConfiguredNetworks();
        
        Log.d("Reflexer", "configured networks: " + configuredNetworks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
