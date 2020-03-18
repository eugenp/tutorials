package com.baeldung.weeknumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeekNumberUsingCalendar {

    public int getWeekNumberFrom(String day, String dateFormat, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance(locale);
        Date date = sdf.parse(day);
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumberFrom(int year, int month, int day, Locale locale) {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.set(year, month, day);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumberFrom(int year, int month, int day, int firstDayOfWeek, int minimalDaysInFirstWeek, Locale locale) {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
        calendar.set(year, month, day);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static void main(String[] args) {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();
        System.out.println(calendar.getWeekNumberFrom(2020, 2, 22, Locale.CANADA));
    }
}
