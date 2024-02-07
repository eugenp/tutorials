package com.baeldung.datetounixtimestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

class DateToUnixTimeStampUnitTest {

    @Test
    void givenDate_whenUsingInstantClass_thenConvertToUnixTimeStamp() {
        Instant givenDate = Instant.parse("2020-09-08T12:16:40Z");

        assertEquals(1599567400L, givenDate.getEpochSecond());
    }

    @Test
    void givenDate_whenUsingLocalDateTimeClass_thenConvertToUnixTimeStamp() {
        LocalDateTime givenDate = LocalDateTime.of(2023, 10, 19, 22, 45);

        assertEquals(1697755500L, givenDate.toEpochSecond(ZoneOffset.UTC));
    }

    @Test
    void givenDate_whenUsingDateClass_thenConvertToUnixTimeStamp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date givenDate = dateFormat.parse("2023-10-15 22:00:00");

        assertEquals(1697407200L, givenDate.getTime() / 1000);
    }

    @Test
    void givenDate_whenUsingCalendarClass_thenConvertToUnixTimeStamp() {
        Calendar calendar = new GregorianCalendar(2023, Calendar.OCTOBER, 17);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        assertEquals(1697500800L, calendar.getTimeInMillis() / 1000);
    }

    @Test
    void givenDate_whenUsingJodaTimeInstantClass_thenConvertToUnixTimeStamp() {
        org.joda.time.Instant givenDate = org.joda.time.Instant.parse("2020-09-08T12:16:40Z");

        assertEquals(1599567400L, givenDate.getMillis() / 1000);
    }

    @Test
    void givenDate_whenUsingJodaTimeDateTimeClass_thenConvertToUnixTimeStamp() {
        DateTime givenDate = new DateTime("2020-09-09T12:16:40Z");

        assertEquals(1599653800L, givenDate.getMillis() / 1000);
    }

}
