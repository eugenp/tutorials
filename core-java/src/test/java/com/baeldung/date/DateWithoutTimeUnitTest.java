package com.baeldung.date;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateWithoutTimeUnitTest {

    private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

    @Test
    public void whenGettingDateWithoutTimeUsingCalendar_thenReturnDateWithoutTime() {
        Date dateWithoutTime = DateWithoutTime.getDateWithoutTimeUsingCalendar();

        // first check the time is set to 0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithoutTime);

        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));

        // now check the difference with the current Date with time is less than a day.
        assertTrue(new Date().getTime() - dateWithoutTime.getTime() < MILLISECONDS_PER_DAY);
    }

    @Test
    public void whenGettingDateWithoutTimeUsingFormat_thenReturnDateWithoutTime() {
        Date dateWithoutTime = null;
        try {
            dateWithoutTime = DateWithoutTime.getDateWithoutTimeUsingFormat();
        } catch (ParseException e) {
            Assert.fail();
        }

        // first check the time is set to 0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithoutTime);

        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));

        // now check the difference with the current Date with time is less than a day.
        assertTrue(new Date().getTime() - dateWithoutTime.getTime() < MILLISECONDS_PER_DAY);
    }

    @Test
    public void whenGettingLocalDate_thenReturnDateWithoutTime() {
        // get the local date
        LocalDate localDate = DateWithoutTime.getLocalDate();

        // get the millis of our LocalDate
        long millisLocalDate = localDate
          .atStartOfDay()
          .toInstant(OffsetDateTime
            .now()
            .getOffset())
          .toEpochMilli();


        // get current millis from Date with time
        long millisDate = new Date().getTime();

        // the difference in time has to be less than a day
        assertTrue(millisDate - millisLocalDate < MILLISECONDS_PER_DAY);
    }

}
