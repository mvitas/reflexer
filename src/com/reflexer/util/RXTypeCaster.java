package com.reflexer.util;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * 
 * @author Vito
 * 
 */
public class RXTypeCaster {

	// TODO: napraviti base typeCaster koji ce useri moci extendati i onda
	// dodavai svoje typeove

	private static RXTypeCaster rxTypeCaster;

	private RXTypeCaster() {
	}

	public static RXTypeCaster getInstance() {
		if (rxTypeCaster == null) {
			rxTypeCaster = new RXTypeCaster();
		}
		return rxTypeCaster;
	}

	public Object initializeType(int rxType, String jsonString) throws JSONException {

		switch (rxType) {
		case RXTypes.RX_BOOL:
			return Boolean.valueOf(new JSONArray(jsonString).getString(0));
		case RXTypes.RX_INT:
			return Integer.valueOf(new JSONArray(jsonString).getString(0));
		case RXTypes.RX_INT_ARRAY:
			return toIntArray(new JSONArray(jsonString));
		case RXTypes.RX_STRING:
			return new JSONArray(jsonString).getString(0);
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
	
	public JSONArray prepareObjectForStorage(int rxType, Object value) throws JSONException {

		switch (rxType) {
		case RXTypes.RX_BOOL:
			return new JSONArray().put(String.valueOf(value));
		case RXTypes.RX_INT:
			return new JSONArray().put(String.valueOf(value));
		case RXTypes.RX_INT_ARRAY:
			JSONArray jIntArray = new JSONArray();
			int [] ints = (int[]) value; 
			for (int i=0; i<ints.length; i++){
				jIntArray.put(ints[i]);
			}
			return jIntArray;
		case RXTypes.RX_STRING:
			return new JSONArray().put(value);
		case RXTypes.RX_STRING_ARRAY:
			JSONArray jStrArray = new JSONArray();
			int [] strs = (int[]) value; 
			for (int i=0; i<strs.length; i++){
				jStrArray.put(strs[i]);
			}
			return jStrArray;
		default:
			return null;
		}

	}

}
