package com.lmac.mapmaker.main.math;

public class Formatter {

	public static String getHex255(int val) {

		String hex = Integer.toHexString(val).toUpperCase();

		if (hex.length() == 1) {
			hex = "0" + hex;
		}

		return hex;
	}

	public static String getHexString(int a, int b, int c, int d) {

		return getHex255(a) + getHex255(b) + getHex255(c) + getHex255(d);

	}

}
