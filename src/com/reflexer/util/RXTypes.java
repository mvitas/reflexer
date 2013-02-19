package com.reflexer.util;

public class RXTypes {

	public static int RX_UNDEFINED = 0;

	public static int RX_BOOL = 1;

	public static int RX_STRING = 2;
	public static int RX_STRING_ARRAY = 3;

	public static int RX_INT = 4;
	public static int RX_INT_ARRAY = 5;

	public static String RX_BOOL_NAME = "rx-bool";

	public static int fromString(String typeString) {
		if (typeString.equals(RX_BOOL_NAME)) {
			return RX_BOOL;
		} else {
			return RX_UNDEFINED;
		}
	}
}
