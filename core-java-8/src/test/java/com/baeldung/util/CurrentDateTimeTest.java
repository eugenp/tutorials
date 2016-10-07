package com.baeldung.util;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class CurrentDateTimeTest {

    @Test
    public void shouldReturnCurrentDate() {

        final LocalDate now = LocalDate.now();
        final Calendar calendar = GregorianCalendar.getInstance();

        assertEquals("10-10-2010".length(), now.toString().length());
        assertEquals(calendar.get(Calendar.DATE), now.get(ChronoField.DAY_OF_MONTH));
        assertEquals(calendar.get(Calendar.MONTH), now.get(ChronoField.MONTH_OF_YEAR) - 1);
        assertEquals(calendar.get(Calendar.YEAR), now.get(ChronoField.YEAR));
    }

    @Test
    public void shouldReturnCurrentTime() {

        final LocalTime now = LocalTime.now();
        final Calendar calendar = GregorianCalendar.getInstance();

        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), now.get(ChronoField.HOUR_OF_DAY));
        assertEquals(calendar.get(Calendar.MINUTE), now.get(ChronoField.MINUTE_OF_HOUR));
        assertEquals(calendar.get(Calendar.SECOND), now.get(ChronoField.SECOND_OF_MINUTE));
    }

    @Test
    public void shouldReturnCurrentTimestamp() {

        final Instant now = Instant.now();
        final Calendar calendar = GregorianCalendar.getInstance();

        assertEquals(calendar.getTimeInMillis() / 1000, now.getEpochSecond());
    }
}
