package com.baeldung.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDate firstDayOfMonth = LocalDate.now()
            .with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth;
    }

    boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    LocalDateTime getStartOfDay(LocalDate localDate) {
        LocalDateTime startofDay = localDate.atStartOfDay();
        return startofDay;
    }

    LocalDateTime getStartOfDayOfLocalDate(LocalDate localDate) {
        LocalDateTime startofDay = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        return startofDay;
    }

    LocalDateTime getStartOfDayAtMinTime(LocalDate localDate) {
        LocalDateTime startofDay = localDate.atTime(LocalTime.MIN);
        return startofDay;
    }

    LocalDateTime getStartOfDayAtMidnightTime(LocalDate localDate) {
        LocalDateTime startofDay = localDate.atTime(LocalTime.MIDNIGHT);
        return startofDay;
    }

    LocalDateTime getEndOfDay(LocalDate localDate) {
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return endOfDay;
    }

    LocalDateTime getEndOfDayFromLocalTime(LocalDate localDate) {
        LocalDateTime endOfDate = LocalTime.MAX.atDate(localDate);
        return endOfDate;
    }

}
