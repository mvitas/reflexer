package com.reflexer.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Vito
 * 
 */
public class RXTypeCaster {

	// TODO: napraviti base typeCaster koji ce useri moci extendati i onda
	// dodavai svoje typeove

	private RXTypeCaster rxTypeCaster;

	private RXTypeCaster() {
	}

	public RXTypeCaster getInstance() {
		if (rxTypeCaster == null) {
			rxTypeCaster = new RXTypeCaster();
		}
		return rxTypeCaster;
	}

	public Object initializeType(int rxType, String name, String jsonString) throws JSONException {

		switch (rxType) {
		case RXTypes.RX_BOOL:
			return Boolean.valueOf(new JSONObject(jsonString).getString(name));
		case RXTypes.RX_INT:
			return Integer.valueOf(new JSONObject(jsonString).getString(name));
		case RXTypes.RX_INT_ARRAY:
			return toIntArray(new JSONArray(jsonString));
		case RXTypes.RX_STRING:
			return new JSONObject(jsonString).getString(name);
		case RXTypes.RX_STRING_ARRAY:
			return toStringArray(new JSONArray(jsonString));
		default:
			return null;
		}

	}

	private int[] toIntArray(JSONArray jsonArray) throws JSONException {
		int[] intArray = new int[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			intArray[i] = jsonArray.getInt(i);
		}

		return intArray;
	}

	private Object toStringArray(JSONArray jsonArray) throws JSONException {
		String[] stringArray = new String[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}

}
