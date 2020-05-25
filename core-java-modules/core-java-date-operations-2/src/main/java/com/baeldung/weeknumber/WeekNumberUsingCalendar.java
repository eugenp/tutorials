package com.baeldung.weeknumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WeekNumberUsingCalendar {

    public int getWeekNumberFrom(String dateString, String dateFormat, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = sdf.parse(dateString);
        
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumberFrom(int year, int month, int day, Locale locale) {
        Calendar calendar = Calendar.getInstance(locale);
        calendar.set(year, month, day);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumberFrom(int year, int month, int day, int firstDayOfWeek, int minimalDaysInFirstWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        calendar.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
        calendar.set(year, month, day);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

}
