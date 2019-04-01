package nju.yufan.yummy.util;

public class Utils {
	public static String generateToken(){
		return Long.toString(Long.parseLong(String.valueOf(Math.random()).substring(2)),36);
	}
}
