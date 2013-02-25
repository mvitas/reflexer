package com.reflexer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.reflexer.service.RXService;

public class RXOnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, RXService.class);
		context.startService(serviceIntent);
	}

}
