package lbs.push.tool;

import java.text.SimpleDateFormat;

public class Variable {

	public static SimpleDateFormat formats = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	public static String errorJson = "{\"flag\":0}";
	public static String correntJson = "{\"flag\":1}";
	public static String updateJson = "{\"flag\":2}";
	public static String testId = "zy2860634b9e5742b2b43acc2e0a22b5f8,test";
	public static String blacklistId = "";
	public static String validJson="{\"flag\":-1}";//没有机会时，返回标识
	public static String switcherror="{\"flag\":-2}";
}
