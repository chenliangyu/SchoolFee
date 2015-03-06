package org.school.fee.support.utils;

public class Md5Util {
	public static String encrypt(String username, String str) {
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(str + username).toLowerCase();
	}
}
