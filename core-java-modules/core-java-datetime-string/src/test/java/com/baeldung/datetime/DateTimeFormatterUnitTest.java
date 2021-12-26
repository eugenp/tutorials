package com.baeldung.datetime;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeFormatterUnitTest {

    @Test
    public void givenDefaultUsLocaleAndDateTimeAndPattern_whenFormatWithDifferentLocales_thenGettingLocalizedDateTimes() {
        Locale.setDefault(Locale.US);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 15, 50, 500);
        String pattern = "dd-MMMM-yyyy HH:mm:ss.SSS";

        DateTimeFormatter defaultTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        DateTimeFormatter plTimeFormatter = DateTimeFormatter.ofPattern(pattern, new Locale("pl", "PL"));
        DateTimeFormatter deTimeFormatter = DateTimeFormatter.ofPattern(pattern).withLocale(Locale.GERMANY);

        Assert.assertEquals("01-January-2018 10:15:50.000", defaultTimeFormatter.format(localDateTime));
        Assert.assertEquals("01-stycznia-2018 10:15:50.000", plTimeFormatter.format(localDateTime));
        Assert.assertEquals("01-Januar-2018 10:15:50.000", deTimeFormatter.format(localDateTime));
    }

    @Test
    public void givenDateTimeAndTimeZone_whenFormatWithDifferentLocales_thenGettingLocalizedZonedDateTimes() {
        Locale.setDefault(Locale.US);
        LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 15, 50, 500);
        ZoneId losAngelesTimeZone = TimeZone.getTimeZone("America/Los_Angeles").toZoneId();

        DateTimeFormatter localizedFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy z", Locale.US);
        DateTimeFormatter frLocalizedFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy z", Locale.FRANCE);
        String formattedDateTime = localizedFormatter.format(ZonedDateTime.of(localDateTime, losAngelesTimeZone));
        String frFormattedDateTime = frLocalizedFormatter.format(ZonedDateTime.of(localDateTime, losAngelesTimeZone));

        System.out.println(formattedDateTime);
        System.out.println(frFormattedDateTime);

        Assert.assertEquals("Monday, January 01, 2018 PST", formattedDateTime);
        Assert.assertEquals("lundi, janvier 01, 2018 PST", frFormattedDateTime);
    }

    @Test
    public void shouldPrintFormattedDate() {
        String europeanDatePattern = "dd.MM.yyyy";
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
        LocalDate summerDay = LocalDate.of(2016, 7, 31);
        Assert.assertEquals("31.07.2016", europeanDateFormatter.format(summerDay));
    }

    @Test
    public void shouldPrintFormattedTime24() {
        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(17, 35, 50);
        Assert.assertEquals("17:35:50", timeColonFormatter.format(colonTime));
    }

    @Test
    public void shouldPrintFormattedTimeWithMillis() {
        String timeColonPattern = "HH:mm:ss SSS";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(17, 35, 50).plus(329, ChronoUnit.MILLIS);
        Assert.assertEquals("17:35:50 329", timeColonFormatter.format(colonTime));
    }

    @Test
    public void shouldPrintFormattedTimePM() {
        String timeColonPattern = "hh:mm:ss a";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime colonTime = LocalTime.of(17, 35, 50);
        Assert.assertEquals("05:35:50 PM", timeColonFormatter.format(colonTime));
    }

    @Test
    public void shouldPrintFormattedUTCRelatedZonedDateTime() {
        String newYorkDateTimePattern = "dd.MM.yyyy HH:mm z";
        DateTimeFormatter newYorkDateFormatter = DateTimeFormatter.ofPattern(newYorkDateTimePattern);
        LocalDateTime summerDay = LocalDateTime.of(2016, 7, 31, 14, 15);
        Assert.assertEquals("31.07.2016 14:15 UTC-04:00", newYorkDateFormatter.format(ZonedDateTime.of(summerDay, ZoneId.of("UTC-4"))));
    }

    @Test
    public void shouldPrintFormattedNewYorkZonedDateTime() {
        String newYorkDateTimePattern = "dd.MM.yyyy HH:mm z";
        DateTimeFormatter newYorkDateFormatter = DateTimeFormatter.ofPattern(newYorkDateTimePattern);
        LocalDateTime summerDay = LocalDateTime.of(2016, 7, 31, 14, 15);
        Assert.assertEquals("31.07.2016 14:15 EDT", newYorkDateFormatter.format(ZonedDateTime.of(summerDay, ZoneId.of("America/New_York"))));
    }

    @Test
    public void shouldPrintStyledDate() {
        LocalDate anotherSummerDay = LocalDate.of(2016, 8, 23);
        Assert.assertEquals("Tuesday, August 23, 2016", DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(anotherSummerDay));
        Assert.assertEquals("August 23, 2016", DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(anotherSummerDay));
        Assert.assertEquals("Aug 23, 2016", DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(anotherSummerDay));
        Assert.assertEquals("8/23/16", DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(anotherSummerDay));
    }

    // Note: The exact output format using the different FormatStyle constants differs by JVM/Java version
    //    @Test
    //    public void shouldPrintStyledDateTime() {
    //        LocalDateTime anotherSummerDay = LocalDateTime.of(2016, 8, 23, 13, 12, 45);
    //        Assert.assertEquals("Tuesday, August 23, 2016 1:12:45 PM EET", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
    //        Assert.assertEquals("August 23, 2016 1:12:45 PM EET", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
    //        Assert.assertEquals("Aug 23, 2016 1:12:45 PM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
    //        Assert.assertEquals("8/23/16 1:12 PM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
    //    }

    @Test
    public void shouldPrintFormattedDateTimeWithPredefined() {
        Assert.assertEquals("2018-03-09", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.of(2018, 3, 9)));
        Assert.assertEquals("2018-03-09-03:00", DateTimeFormatter.ISO_OFFSET_DATE.format(LocalDate.of(2018, 3, 9).atStartOfDay(ZoneId.of("UTC-3"))));
        Assert.assertEquals("Fri, 9 Mar 2018 00:00:00 -0300", DateTimeFormatter.RFC_1123_DATE_TIME.format(LocalDate.of(2018, 3, 9).atStartOfDay(ZoneId.of("UTC-3"))));
    }

    @Test
    public void shouldParseDateTime() {
        Assert.assertEquals(LocalDate.of(2018, 3, 12), LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse("2018-03-09")).plusDays(3));
    }

    // Note: The exact output format using the different FormatStyle constants differs by JVM/Java version
    //    @Test
    //    public void shouldParseFormatStyleFull() {
    //        ZonedDateTime dateTime = ZonedDateTime.from(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).parse("Tuesday, August 23, 2016 1:12:45 PM EET"));
    //        Assert.assertEquals(ZonedDateTime.of(LocalDateTime.of(2016, 8, 23, 22, 12, 45), ZoneId.of("Europe/Bucharest")), dateTime.plusHours(9));
    //    }

    @Test
    public void shouldParseDateWithCustomFormatter() {
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Assert.assertFalse(LocalDate.from(europeanDateFormatter.parse("15.08.2014")).isLeapYear());
    }

    @Test
    public void shouldParseTimeWithCustomFormatter() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        Assert.assertTrue(LocalTime.from(timeFormatter.parse("12:25:30 AM")).isBefore(LocalTime.NOON));
    }

    @Test
    public void shouldParseZonedDateTimeWithCustomFormatter() {
        DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm z");
        Assert.assertEquals(7200, ZonedDateTime.from(zonedFormatter.parse("31.07.2016 14:15 GMT+02:00")).getOffset().getTotalSeconds());
    }

    @Test(expected = DateTimeParseException.class)
    public void shouldExpectAnExceptionIfDateTimeStringNotMatchPattern() {
        DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm z");
        ZonedDateTime.from(zonedFormatter.parse("31.07.2016 14:15"));
    }

    @Test
    public void shouldPrintFormattedZonedDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2021, 02, 15, 0, 0, 0, 0, ZoneId.of("Europe/Paris"));
        String formattedZonedDateTime = DateTimeFormatter.ISO_INSTANT.format(zonedDateTime);
        
        Assert.assertEquals("2021-02-14T23:00:00Z", formattedZonedDateTime);
    }
    
    @Test(expected = UnsupportedTemporalTypeException.class)
    public void shouldExpectAnExceptionIfInputIsLocalDateTime() {
        DateTimeFormatter.ISO_INSTANT.format(LocalDate.now());
    }
    
    @Test
    public void shouldParseZonedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-10-01T05:06:20Z", formatter);
        
        Assert.assertEquals("2021-10-01T05:06:20Z", DateTimeFormatter.ISO_INSTANT.format(zonedDateTime));
    }
    
    @Test(expected = DateTimeParseException.class)
    public void shouldExpectAnExceptionIfTimeZoneIsMissing() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-11-01T05:06:20Z", DateTimeFormatter.ISO_INSTANT);
    }
    
    @Test(expected = DateTimeParseException.class)
    public void shouldExpectAnExceptionIfSecondIsMissing() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-12-02T08:06Z", DateTimeFormatter.ISO_INSTANT);
    }
}
