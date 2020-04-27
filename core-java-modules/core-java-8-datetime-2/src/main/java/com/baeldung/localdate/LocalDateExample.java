package com.baeldung.localdate;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class LocalDateExample {
    public LocalDate getCustomDateOne(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    public LocalDate getCustomDateTwo(int year, Month month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    public LocalDate getDateFromEpochDay(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }

    public LocalDate getDateFromYearAndDayOfYear(int year, int dayOfYear) {
        return LocalDate.ofYearDay(year, dayOfYear);
    }

    public LocalDate getDateFromString(String date) {
        return LocalDate.parse(date);
    }

    public LocalDate getDateFromStringAndFormatter(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
}
