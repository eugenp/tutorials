package com.baeldung.rounddate;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateRoundingUnitTest {

    @Test
    public void givenDate_whenRoundToDay_thenBeginningOfDay() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 30);
        Date result = RoundDate.roundToDay(date);
        assertEquals(RoundDate.getDate(2023, Calendar.JANUARY, 27, 0, 0), result);
    }

    @Test
    public void givenDate_whenRoundToNearestUnit_thenNearestUnit() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 12);
        Date result = RoundDate.roundToNearestUnit(date, Calendar.DAY_OF_MONTH);
        Date expected = RoundDate.getDate(2023, Calendar.JANUARY, 28, 0, 0);
        assertEquals(expected, result);
    }

    @Test
    public void givenLocalDateTime_whenRoundToStartOfMonth_thenStartOfMonth() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 27, 14, 12);
        LocalDateTime result = RoundDate.roundToStartOfMonthUsingLocalDateTime(dateTime);
        LocalDateTime expected = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
        assertEquals(expected, result);
    }


    @Test
    public void givenZonedDateTime_whenRoundToStartOfMonth_thenStartOfMonth() {
        ZonedDateTime dateTime = ZonedDateTime.of(2023, 1, 27, 14, 12, 0, 0, ZoneId.systemDefault());
        ZonedDateTime result = RoundDate.roundToStartOfMonthUsingZonedDateTime(dateTime);
        ZonedDateTime expected = ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        assertEquals(expected, result);
    }

    @Test
    public void givenLocalDateTime_whenRoundToEndOfWeek_thenEndOfWeek() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 27, 14, 12);
        LocalDateTime result = RoundDate.roundToEndOfWeekUsingLocalDateTime(dateTime);
        LocalDateTime expected = LocalDateTime.of(2023, 1, 28, 23, 59, 59, 999);
        assertEquals(expected, result);
    }

    @Test
    public void givenZonedDateTime_whenRoundToEndOfWeek_thenEndOfWeek() {
        ZonedDateTime dateTime = ZonedDateTime.of(2023, 1, 27, 14, 12, 0, 0, ZoneId.systemDefault());
        ZonedDateTime result = RoundDate.roundToEndOfWeekUsingZonedDateTime(dateTime);
        ZonedDateTime expected = ZonedDateTime.of(2023, 1, 28, 23, 59, 59, 999, ZoneId.systemDefault());
        assertEquals(expected, result);
    }

}
