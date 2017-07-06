package com.example.administrator.zhixiao10.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTime {



	public static String getTime() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.format(date);
	}

	public static String getTime(Long time) {
		Date date = new Date(time);
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.format(date);
	}


	public static Long getTime(String dateString) throws ParseException {
		SimpleDateFormat formate = new SimpleDateFormat("MM-dd HH:mm:ss");
		return formate.parse(dateString).getTime();
	}
}
