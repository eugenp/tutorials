package com.baeldung.date.comparison;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateComparisonUtils {

	public static boolean isSameDayUsingLocalDate(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate1.isEqual(localDate2);
	}

	public static boolean isSameDayUsingSimpleDateFormat(Date date1, Date date2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date1).equals(fmt.format(date2));
	}

	public static boolean isSameDayUsingCalendar(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);

	}

	public static boolean isSameDayUsingApacheCommons(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}
}
