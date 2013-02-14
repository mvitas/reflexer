package com.reflexer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class RXService extends Service {

	@Override
    public void onCreate() {
        super.onCreate();

//        final IntentFilter theFilter = new IntentFilter();
//        theFilter.addAction(ACTION);
//
//        this.yourReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                // Do whatever you need it to do when it receives the broadcast
//                // Example show a Toast message...
//                showSuccessfulBroadcast();
//            }
//        };
//
////         Registers the receiver so that your service will listen for broadcasts
//        this.registerReceiver(this.yourReceiver, theFilter);
    }
	
	@Override
    public void onDestroy() {
        super.onDestroy();

        // Do not forget to unregister the receiver!!!
//        this.unregisterReceiver(this.yourReceiver);
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
