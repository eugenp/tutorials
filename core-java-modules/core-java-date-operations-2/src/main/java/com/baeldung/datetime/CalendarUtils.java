package com.baeldung.datetime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static Calendar getPlusDays(Date date, int amount) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        return calendar;
    }
}