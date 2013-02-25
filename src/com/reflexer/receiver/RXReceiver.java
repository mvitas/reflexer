package com.reflexer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.reflexer.handler.RXHandler;
import com.reflexer.model.RXStimuli;
import com.reflexer.model.RXStimuliDefinition;
import com.reflexer.service.RXService;

import java.io.IOException;
import java.util.ArrayList;
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
		ArrayList<RXStimuliDefinition> definitions = null;
		try {
			definitions = RXStimuli.getStimuliDefinitions(service);
		} catch (IOException e) {
			// TODO: notify service
			return;
		}

		for (int i = 0; i < definitions.size(); i++) {
			final RXHandler handler = definitions.get(i).getHandler();

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
