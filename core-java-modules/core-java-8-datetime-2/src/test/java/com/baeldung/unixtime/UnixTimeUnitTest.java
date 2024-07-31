package com.baeldung.unixtime;

import static org.junit.Assert.assertEquals;
import java.time.Instant;
import java.time.LocalDate;

import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import org.joda.time.DateTime;

import org.junit.jupiter.api.Test;

public class UnixTimeUnitTest {

    @Test
    public void givenTimeUsingDateApi_whenConvertedToUnixTime_thenMatch() {
        Date date = new Date(2023 - 1900, 1, 15, 0, 0, 0);
        long expected = 1676419200;
        long actual = date.getTime() / 1000L;
        assertEquals(expected, actual);
    }

    @Test
    public void givenTimeUsingJodaTime_whenConvertedToUnixTime_thenMatch() {
        DateTime dateTime = new DateTime(2023, 2, 15, 00, 00, 00, 0);
        long expected = 1676419200;
        long actual = dateTime.getMillis() / 1000L;
        assertEquals(expected, actual);
    }

    @Test
    public void givenTimeUsingLocalDate_whenConvertedToUnixTime_thenMatch() {
        LocalDate date = LocalDate.of(2023, Month.FEBRUARY, 15);
        Instant instant = date.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        long expected = 1676419200;
        long actual = instant.getEpochSecond();
        assertEquals(expected, actual);
    }

}
