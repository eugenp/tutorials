package com.baeldung.sundaystartyears;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FindSundayStartYearsTimeApi {

    public static List<Integer> getYearsStartingOnSunday(int startYear, int endYear) {
        List<Integer> years = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
            if (firstDayOfYear.getDayOfWeek() == DayOfWeek.SUNDAY) {
                years.add(year);
            }
        }
        return years;
    }
}