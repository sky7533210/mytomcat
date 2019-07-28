package com.sky.tomcat.util;

public class Logger {
	public static void log(String info) {
		System.out.println("[INFO]"+DateUtil.getCurrentTime()+" "+info);
	}
	public static void error(String error) {
		System.err.println("[ERROR]"+DateUtil.getCurrentTime()+" "+error);
	}
}
