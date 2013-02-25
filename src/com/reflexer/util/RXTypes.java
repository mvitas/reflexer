package com.reflexer.util;

public class RXTypes {

	public static final int RX_BOOL = 1;

	public static final int RX_STRING = 2;
	public static final int RX_STRING_ARRAY = 3;

	public static final int RX_INT = 4;
	public static final int RX_INT_ARRAY = 5;

	public static final String RX_BOOL_NAME = "rx-bool";

	public static final String RX_STRING_NAME = "rx-string";

	public static final String RX_STRING_ARRAY_NAME = "rx-string-array";

	public static final String RX_INT_NAME = "rx-int";

	public static final String RX_INT_ARRAY_NAME = "rx-int-array";

	public static int fromString(String typeString) {
		if (typeString.equals(RX_BOOL_NAME)) {
			return RX_BOOL;
		} else if (typeString.equals(RX_STRING_NAME)) {
			return RX_STRING;
		} else if (typeString.equals(RX_STRING_ARRAY_NAME)) {
			return RX_STRING_ARRAY;
		} else if (typeString.equals(RX_INT_NAME)) {
			return RX_INT;
		} else if (typeString.equals(RX_INT_ARRAY_NAME)) {
			return RX_INT_ARRAY;
		} else {
			throw new IllegalArgumentException("Unknown type");
		}
	}

}
