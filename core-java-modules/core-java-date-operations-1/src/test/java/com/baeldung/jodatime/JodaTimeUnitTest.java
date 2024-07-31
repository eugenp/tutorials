package com.baeldung.jodatime;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class JodaTimeUnitTest {

    @Test
    public void testDateTimeRepresentation() {

        DateTimeZone.setDefault(DateTimeZone.forID("Europe/Bucharest"));

        // representing current date and time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        LocalDateTime currentDateTimeFromJavaDate = new LocalDateTime(new Date());
        Date currentJavaDate = currentDateTimeFromJavaDate.toDate();

        // representing custom date and time
        Date oneMinuteAgoDate = new Date(System.currentTimeMillis() - (60 * 1000));
        Instant oneMinutesAgoInstant = new Instant(oneMinuteAgoDate);

        DateTime customDateTimeFromInstant = new DateTime(oneMinutesAgoInstant);
        DateTime customDateTimeFromJavaDate = new DateTime(oneMinuteAgoDate);
        DateTime customDateTimeFromString = new DateTime("2018-05-05T10:11:12.123");
        DateTime customDateTimeFromParts = new DateTime(2018, 5, 5, 10, 11, 12, 123);

        // parsing
        DateTime parsedDateTime = DateTime.parse("2018-05-05T10:11:12.123");
        assertEquals("2018-05-05T10:11:12.123+03:00", parsedDateTime.toString());

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        DateTime parsedDateTimeUsingFormatter = DateTime.parse("05/05/2018 10:11:12", dateTimeFormatter);
        assertEquals("2018-05-05T10:11:12.000+03:00", parsedDateTimeUsingFormatter.toString());

        // Instant
        Instant instant = new Instant();
        Instant.now();

        Instant instantFromString = new Instant("2018-05-05T10:11:12");
        Instant instantFromDate = new Instant(oneMinuteAgoDate);
        Instant instantFromTimestamp = new Instant(System.currentTimeMillis() - (60 * 1000));
        Instant parsedInstant = Instant.parse("05/05/2018 10:11:12", dateTimeFormatter);

        Instant instantNow = Instant.now();
        Instant oneMinuteAgoInstant = new Instant(oneMinuteAgoDate);

        // epochMilli and epochSecond
        long milliesFromEpochTime = System.currentTimeMillis();
        long secondsFromEpochTime = milliesFromEpochTime / 1000;
        Instant instantFromEpochMilli = Instant.ofEpochMilli(milliesFromEpochTime);
        Instant instantFromEpocSeconds = Instant.ofEpochSecond(secondsFromEpochTime);

        // convert Instants
        DateTime dateTimeFromInstant = instant.toDateTime();
        Date javaDateFromInstant = instant.toDate();

        int year = instant.get(DateTimeFieldType.year());
        int month = instant.get(DateTimeFieldType.monthOfYear());
        int day = instant.get(DateTimeFieldType.dayOfMonth());
        int hour = instant.get(DateTimeFieldType.hourOfDay());

        // Duration, Period, Instant
        long currentTimestamp = System.currentTimeMillis();
        long oneHourAgo = currentTimestamp - 24*60*1000;

        Duration duration = new Duration(oneHourAgo, currentTimestamp);
        Instant.now().plus(duration);

        long durationInDays = duration.getStandardDays();
        long durationInHours = duration.getStandardHours();
        long durationInMinutes = duration.getStandardMinutes();
        long durationInSeconds = duration.getStandardSeconds();
        long durationInMilli = duration.getMillis();

        // converting between classes
        DateTimeUtils.setCurrentMillisFixed(currentTimestamp);
        LocalDateTime currentDateAndTime  = LocalDateTime.now();

        assertEquals(new DateTime(currentTimestamp), currentDateAndTime.toDateTime());
        assertEquals(new LocalDate(currentTimestamp), currentDateAndTime.toLocalDate());
        assertEquals(new LocalTime(currentTimestamp), currentDateAndTime.toLocalTime());
    }

    @Test
    public void testJodaInstant() {

        Date oneMinuteAgoDate = new Date(System.currentTimeMillis() - (60 * 1000));

        Instant instantNow = Instant.now();
        Instant oneMinuteAgoInstant = new Instant(oneMinuteAgoDate);

        assertTrue(instantNow.compareTo(oneMinuteAgoInstant) > 0);
        assertTrue(instantNow.isAfter(oneMinuteAgoInstant));
        assertTrue(oneMinuteAgoInstant.isBefore(instantNow));
        assertTrue(oneMinuteAgoInstant.isBeforeNow());
        assertFalse(oneMinuteAgoInstant.isEqual(instantNow));

        LocalDateTime localDateTime = new LocalDateTime("2018-02-01");
        Period period = new Period().withMonths(1);
        LocalDateTime datePlusPeriod = localDateTime.plus(period);

        Instant startInterval1 = new Instant("2018-05-05T09:00:00.000");
        Instant endInterval1 = new Instant("2018-05-05T11:00:00.000");
        Interval interval1 = new Interval(startInterval1, endInterval1);

        Instant startInterval2 = new Instant("2018-05-05T10:00:00.000");
        Instant endInterval2 = new Instant("2018-05-05T11:00:00.000");
        Interval interval2 = new Interval(startInterval2, endInterval2);

        Instant startInterval3 = new Instant("2018-05-05T11:00:00.000");
        Instant endInterval3 = new Instant("2018-05-05T13:00:00.000");
        Interval interval3 = new Interval(startInterval3, endInterval3);

        Interval overlappingInterval = interval1.overlap(interval2);
        Interval notOverlappingInterval = interval1.overlap(interval3);

        assertTrue(overlappingInterval.isEqual(new Interval(new Instant("2018-05-05T10:00:00.000"), new Instant("2018-05-05T11:00:00.000"))));
        assertNotNull(overlappingInterval);

        interval1.abuts(interval3);
        assertTrue(interval1.abuts(new Interval(new Instant("2018-05-05T11:00:00.000"), new Instant("2018-05-05T13:00:00.000"))));

        interval1.gap(interval2);
    }


    @Test
    public void testDateTimeOperations() {

        DateTimeUtils.setCurrentMillisFixed(1529612783288L);
        DateTimeZone.setDefault(DateTimeZone.UTC);

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        assertEquals("2018-06-21T20:26:23.288", currentLocalDateTime.toString());

        LocalDateTime nextDayDateTime = currentLocalDateTime.plusDays(1);
        assertEquals("2018-06-22T20:26:23.288", nextDayDateTime.toString());

        Period oneMonth = new Period().withMonths(1);
        LocalDateTime nextMonthDateTime = currentLocalDateTime.plus(oneMonth);
        assertEquals("2018-07-21T20:26:23.288", nextMonthDateTime.toString());

        LocalDateTime previousDayLocalDateTime = currentLocalDateTime.minusDays(1);
        assertEquals("2018-06-20T20:26:23.288", previousDayLocalDateTime.toString());

        LocalDateTime currentDateAtHour10 = currentLocalDateTime
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0);
        assertEquals("2018-06-21T00:00:00.000", currentDateAtHour10.toString());
    }

    @Test
    public void testTimezones() {

        System.getProperty("user.timezone");
        DateTimeZone.getAvailableIDs();
        // DateTimeZone.setDefault(DateTimeZone.forID("Europe/Bucharest"));

        DateTimeUtils.setCurrentMillisFixed(1529612783288L);

        DateTime dateTimeInChicago = new DateTime(DateTimeZone.forID("America/Chicago"));
        assertEquals("2018-06-21T15:26:23.288-05:00", dateTimeInChicago.toString());

        DateTime dateTimeInBucharest = new DateTime(DateTimeZone.forID("Europe/Bucharest"));
        assertEquals("2018-06-21T23:26:23.288+03:00", dateTimeInBucharest.toString());

        LocalDateTime localDateTimeInChicago = new LocalDateTime(DateTimeZone.forID("America/Chicago"));
        assertEquals("2018-06-21T15:26:23.288", localDateTimeInChicago.toString());

        DateTime convertedDateTime = localDateTimeInChicago.toDateTime(DateTimeZone.forID("Europe/Bucharest"));
        assertEquals("2018-06-21T15:26:23.288+03:00", convertedDateTime.toString());
    }

}
