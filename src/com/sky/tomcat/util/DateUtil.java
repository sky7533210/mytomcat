package com.sky.tomcat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	private DateUtil() {}
	public static String getCurrentTime() {
		return dateFormat.format(new Date());
	}
}
