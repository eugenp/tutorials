package com.baeldung.getweeknumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetWeekNumberOldJava {
	private Calendar calendar;

	public GetWeekNumberOldJava(String day, String dayFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dayFormat);

		calendar = Calendar.getInstance();
		Date date = sdf.parse(day);
		calendar.setTime(date);
	}

	public GetWeekNumberOldJava(int year, int month, int day) {
		calendar = Calendar.getInstance();
		calendar.set(year, month, day);
	}

	public int getDayOfWeek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static void main(String[] args) throws ParseException {
		GetWeekNumberOldJava example1 = new GetWeekNumberOldJava(2020, 2, 2);
		System.out.println(example1.getDayOfWeek());

		GetWeekNumberOldJava example2 = new GetWeekNumberOldJava("20200302", "yyyyMMdd");
		System.out.println(example2.getDayOfWeek());
	}
}
