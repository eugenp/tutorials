package com.baeldung.convert;

import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class ConvertDateTimeUnitTest {

    public final long millis = 1556175797428L;

    @Test
    public void givenLocalDateTime_WhenToEpochMillis_ThenMillis() {
        ZoneId id = ZoneId.systemDefault();

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(millis), id);

        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, id);

        Assert.assertEquals(millis, zdt.toInstant().toEpochMilli());
    }

    @Test
    public void givenJava8Instant_WhenGToEpochMillis_ThenMillis() {
        java.time.Instant instant = java.time.Instant.ofEpochMilli(millis);
        Assert.assertEquals(millis, instant.toEpochMilli());
    }

    @Test
    public void givenDate_WhenGetTime_ThenMillis() {
        Date date = new Date(millis);
        Assert.assertEquals(millis, date.getTime());
    }

    @Test
    public void givenCalendar_WhenGetTimeInMillis_ThenMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(millis));
        Assert.assertEquals(millis, calendar.getTimeInMillis());
    }

    @Test
    public void givenJodaInstant_WhenGetMillis_ThenMillis() {
        Instant jodaInstant = Instant.ofEpochMilli(millis);
        Assert.assertEquals(millis, jodaInstant.getMillis());
    }

    @Test
    public void givenJODADateTime_WhenGetMillis_ThenMillis() {
        org.joda.time.DateTime jodaDateTime = new org.joda.time.DateTime(millis);
        Assert.assertEquals(millis, jodaDateTime.getMillis());
    }
}
