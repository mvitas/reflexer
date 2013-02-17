package com.reflexer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.reflexer.handler.RXHandler;
import com.reflexer.service.RXService;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class RXReceiver extends BroadcastReceiver {

	private final static int CORE_THREAD_POOL_SIZE = 2;

	private final RXService service;

	private final ScheduledThreadPoolExecutor executor;

	public RXReceiver(RXService service) {
		this.service = service;
		this.executor = new ScheduledThreadPoolExecutor(CORE_THREAD_POOL_SIZE);
	}

	@Override
	public void onReceive(Context context, final Intent intent) {
		for (int i = 0; i < service.getHandlers().size(); i++) {
			final RXHandler handler = service.getHandlers().get(i);

			if (handler.isInterestingAction(intent.getAction())) {
				executor.execute(new Runnable() {

					@Override
					public void run() {
						handler.onReceive(service, intent);
					}
				});
			}
		}
	}
}
