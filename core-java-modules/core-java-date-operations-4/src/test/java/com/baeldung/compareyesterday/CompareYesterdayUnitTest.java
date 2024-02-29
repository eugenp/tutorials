package com.baeldung.compareyesterday;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;

public class CompareYesterdayUnitTest {

    @Test
    void givenYesterdayDate_whenCompareWithCalendar_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.setTime(date);

        Calendar yesterdayDate = Calendar.getInstance();
        yesterdayDate.add(Calendar.DATE, -1);

        boolean isEqualToYesterday =
            yesterdayDate.get(Calendar.YEAR) == testCalendar.get(Calendar.YEAR) && yesterdayDate.get(Calendar.MONTH) == testCalendar.get(Calendar.MONTH) &&
                yesterdayDate.get(Calendar.DAY_OF_MONTH) == testCalendar.get(Calendar.DAY_OF_MONTH);
        assert (isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithCalendar_returnFalse() {
        Calendar testCalendar = Calendar.getInstance();
        testCalendar.setTime(new Date());

        Calendar yesterdayDate = Calendar.getInstance();
        yesterdayDate.add(Calendar.DATE, -1);

        boolean isEqualToYesterday =
            yesterdayDate.get(Calendar.YEAR) == testCalendar.get(Calendar.YEAR) && yesterdayDate.get(Calendar.MONTH) == testCalendar.get(Calendar.MONTH) &&
                yesterdayDate.get(Calendar.DAY_OF_MONTH) == testCalendar.get(Calendar.DAY_OF_MONTH);
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithDateMilliseconds_returnTrue() {
        // Create a Date object representing yesterday
        Date testDate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        long yesterdayMidnightMillis = Instant.now()
            .minus(1, ChronoUnit.DAYS)
            .toEpochMilli();

        boolean isEqualToYesterday = testDate.getTime() >= yesterdayMidnightMillis && testDate.getTime() < yesterdayMidnightMillis + 86400000;
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithDateMilliseconds_returnFalse() {
        // Create a Date object representing yesterday
        Date testDate = new Date();
        long yesterdayMidnightMillis = Instant.now()
            .minus(1, ChronoUnit.DAYS)
            .toEpochMilli();

        boolean isEqualToYesterday = testDate.getTime() >= yesterdayMidnightMillis && testDate.getTime() < yesterdayMidnightMillis + 86400000;
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithLocalDate_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        LocalDate testLocalDate = LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());

        LocalDate yesterdayDate = LocalDate.now()
            .minusDays(1);
        boolean isEqualToYesterday = testLocalDate.equals(yesterdayDate);
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithLocalDate_returnFalse() {
        LocalDate testLocalDate = LocalDate.now();
        LocalDate yesterdayDate = testLocalDate.minusDays(1);

        boolean isEqualToYesterday = testLocalDate.equals(yesterdayDate);
        assertFalse(isEqualToYesterday);
    }

    @Test
    void givenYesterdayDate_whenCompareWithJodaTime_returnTrue() {
        // To simulate yesterday
        Date date = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        DateTime testDateTime = new DateTime(date).withTimeAtStartOfDay();

        DateTime yesterdayDateTime = DateTime.now()
            .minusDays(1)
            .withTimeAtStartOfDay();

        boolean isEqualToYesterday = testDateTime.equals(yesterdayDateTime);
        assertTrue(isEqualToYesterday);
    }

    @Test
    void givenTodayDate_whenCompareWithJodaTime_returnFalse() {
        DateTime testDateTime = DateTime.now()
            .withTimeAtStartOfDay();
        DateTime yesterdayDateTime = testDateTime.minusDays(1)
            .withTimeAtStartOfDay();

        boolean isEqualToYesterday = testDateTime.equals(yesterdayDateTime);
        assertFalse(isEqualToYesterday);
    }
}
