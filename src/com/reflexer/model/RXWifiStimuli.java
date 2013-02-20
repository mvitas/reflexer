package com.reflexer.model;

import com.reflexer.util.RXTypes;

import java.util.ArrayList;

public class RXWifiStimuli extends RXStimuli {

	public final static String CONDITION_CONNECTED = "connected";

	public final static String CONDITION_NETWORK_NAME = "network-name";

	@Override
	public ArrayList<RXCondition> getConditionList() {
		ArrayList<RXCondition> conditionList = new ArrayList<RXCondition>();
		RXCondition wifiConnectedCondition = new RXCondition(true, CONDITION_CONNECTED, RXTypes.RX_BOOL,
				new ArrayList<RXCondition>());

		RXCondition networkNameCondition = new RXCondition(false, CONDITION_NETWORK_NAME, RXTypes.RX_STRING,
				new ArrayList<RXCondition>());

		conditionList.add(wifiConnectedCondition);
		conditionList.add(networkNameCondition);

		return conditionList;
	}

}
