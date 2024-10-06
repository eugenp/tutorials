package com.baeldung.sundaystartyears;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FindSundayStartYearsLegacy {

    public static List<Integer> getYearsStartingOnSunday(int startYear, int endYear) {
        List<Integer> years = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, 1);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                years.add(year);
            }
        }
        return years;
    }
}