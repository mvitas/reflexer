package com.reflexer.util;

public class RXTypes {

	public static int RX_UNDEFINED = 0;

	public static int RX_BOOL = 1;

	public static int RX_STRING = 2;
	public static int RX_STRING_ARRAY = 3;

	public static int RX_INT = 4;
	public static int RX_INT_ARRAY = 5;

	public static String RX_BOOL_NAME = "rx-bool";

	public static String RX_STRING_NAME = "rx-string";

	public static String RX_STRING_ARRAY_NAME = "rx-string-array";

	public static String RX_INT_NAME = "rx-int";

	public static String RX_INT_ARRAY_NAME = "rx-int-array";

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
