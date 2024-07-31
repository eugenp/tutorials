package com.baeldung.longtodate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.junit.jupiter.api.Test;

class LongToDateUnitTest {

    @Test
    void givenLongValue_whenUsingInstantClass_thenConvert() {
        Instant expectedDate = Instant.parse("2020-09-08T12:16:40Z");
        long seconds = 1599567400L;

        Instant date = Instant.ofEpochSecond(seconds);

        assertEquals(expectedDate, date);
    }

    @Test
    void givenLongValue_whenUsingLocalDateClass_thenConvert() {
        LocalDate expectedDate = LocalDate.of(2023, 10, 17);
        long epochDay = 19647L;

        LocalDate date = LocalDate.ofEpochDay(epochDay);

        assertEquals(expectedDate, date);
    }

    @Test
    void givenLongValue_whenUsingDateClass_thenConvert() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date expectedDate = dateFormat.parse("2023-07-15 22:00:00");
        long milliseconds = 1689458400000L;

        Date date = new Date(milliseconds);

        assertEquals(expectedDate, date);
    }

    @Test
    void givenLongValue_whenUsingCalendarClass_thenConvert() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date expectedDate = dateFormat.parse("2023-07-15 22:00:00");
        long milliseconds = 1689458400000L;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(milliseconds);

        assertEquals(expectedDate, calendar.getTime());
    }

    @Test
    void givenLongValue_whenUsingJodaTimeLocalDateClass_thenConvert() {
        org.joda.time.LocalDate expectedDate = new org.joda.time.LocalDate(2023, 7, 15);
        long milliseconds = 1689458400000L;

        org.joda.time.LocalDate date = new org.joda.time.LocalDate(milliseconds, DateTimeZone.UTC);

        assertEquals(expectedDate, date);
    }

}
