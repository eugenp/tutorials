package com.baeldung.weeknumber;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekNumberUsingLocalDate {

    public Integer getWeekNumberUsingWeekFiedsFrom(String day, String dayFormat, Locale locale) {
        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ofPattern(dayFormat));

        return date.get(WeekFields.of(locale)
            .weekOfYear());
    }

    public Integer getWeekNumberUsingWeekFieldsFrom(int year, int month, int day, Locale locale) {
        LocalDate date = LocalDate.of(year, month, day);

        return date.get(WeekFields.of(locale)
            .weekOfYear());
    }

    public Integer getWeekNumberUsingChronoFieldFrom(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);

        return date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }
}