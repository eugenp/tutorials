package com.baeldung.compareyesterday;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class CompareYesterdayUnitTest {

    @Test
    void givenYesterdayDate_whenCompareWithCalendar_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(date);

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.add(Calendar.DATE, -1);

        boolean isEqualToYesterday = expectedCalendar.get(Calendar.YEAR) == actualCalendar.get(Calendar.YEAR) &&
            expectedCalendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH) &&
            expectedCalendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH);
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithCalendar_returnFalse() {
        Calendar actualCalendar = Calendar.getInstance();

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.add(Calendar.DATE, -1);

        boolean isEqualToYesterday = expectedCalendar.get(Calendar.YEAR) == actualCalendar.get(Calendar.YEAR) &&
            expectedCalendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH) &&
            expectedCalendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH);
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithDateMilliseconds_returnTrue() {
        // Create a Date object representing yesterday
        Date actualDate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        long yesterdayMidnightMillis = Instant.now()
            .minus(1, ChronoUnit.DAYS)
            .toEpochMilli();

        boolean isEqualToYesterday = actualDate.getTime() >= yesterdayMidnightMillis && actualDate.getTime() < yesterdayMidnightMillis + 86_400_000;
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithDateMilliseconds_returnFalse() {
        // Create a Date object representing yesterday
        Date actualDate = new Date();
        long yesterdayMidnightMillis = Instant.now()
            .minus(1, ChronoUnit.DAYS)
            .toEpochMilli();

        boolean isEqualToYesterday = actualDate.getTime() >= yesterdayMidnightMillis && actualDate.getTime() < yesterdayMidnightMillis + 86_400_000;
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithLocalDate_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        LocalDate actualLocalDate = LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());

        LocalDate expectedLocalDate = LocalDate.now()
            .minusDays(1);
        boolean isEqualToYesterday = actualLocalDate.equals(expectedLocalDate);
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithLocalDate_returnFalse() {
        LocalDate actualLocalDate = LocalDate.now();
        LocalDate expectedLocalDate = actualLocalDate.minusDays(1);

        boolean isEqualToYesterday = actualLocalDate.equals(expectedLocalDate);
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithJodaTime_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        DateTime actualDateTime = new DateTime(date).withTimeAtStartOfDay();

        DateTime expectedDateTime = DateTime.now()
            .minusDays(1)
            .withTimeAtStartOfDay();

        boolean isEqualToYesterday = actualDateTime.equals(expectedDateTime);
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithJodaTime_returnFalse() {
        DateTime actualDateTime = DateTime.now()
            .withTimeAtStartOfDay();
        DateTime expectedDateTime = actualDateTime.minusDays(1)
            .withTimeAtStartOfDay();

        boolean isEqualToYesterday = actualDateTime.equals(expectedDateTime);
        assertFalse(isEqualToYesterday);
    }
}
