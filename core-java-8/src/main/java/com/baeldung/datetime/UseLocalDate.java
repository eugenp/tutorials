package com.baeldung.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

class UseLocalDate {

    LocalDate getLocalDateUsingFactoryOfMethod(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    LocalDate getLocalDateUsingParseMethod(String representation) {
        return LocalDate.parse(representation);
    }

    LocalDate getLocalDateFromClock() {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

    LocalDate getNextDay(LocalDate localDate) {
        return localDate.plusDays(1);
    }

    LocalDate getPreviousDay(LocalDate localDate) {
        return localDate.minus(1, ChronoUnit.DAYS);
    }

    DayOfWeek getDayOfWeek(LocalDate localDate) {
        DayOfWeek day = localDate.getDayOfWeek();
        return day;
    }

    LocalDate getFirstDayOfMonth() {
        LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth;
    }

    LocalDateTime getStartOfDay(LocalDate localDate) {
        LocalDateTime startofDay = localDate.atStartOfDay();
        return startofDay;
    }
}
