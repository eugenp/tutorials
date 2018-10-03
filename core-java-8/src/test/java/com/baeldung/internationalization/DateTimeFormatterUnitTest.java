package com.baeldung.internationalization;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
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

        DateTimeFormatter localizedFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
        DateTimeFormatter frLocalizedFormatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.FRANCE);
        String formattedDateTime = localizedFormatter.format(ZonedDateTime.of(localDateTime, losAngelesTimeZone));
        String frFormattedDateTime = frLocalizedFormatter.format(ZonedDateTime.of(localDateTime, losAngelesTimeZone));

        Assert.assertEquals("Monday, January 1, 2018 10:15:50 AM PST", formattedDateTime);
        Assert.assertEquals("lundi 1 janvier 2018 10 h 15 PST", frFormattedDateTime);
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

    @Test
    public void shouldPrintStyledDateTime() {
        LocalDateTime anotherSummerDay = LocalDateTime.of(2016, 8, 23, 13, 12, 45);
        Assert.assertEquals("Tuesday, August 23, 2016 1:12:45 PM EET", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
        Assert.assertEquals("August 23, 2016 1:12:45 PM EET", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
        Assert.assertEquals("Aug 23, 2016 1:12:45 PM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
        Assert.assertEquals("8/23/16 1:12 PM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.of("Europe/Helsinki")).format(anotherSummerDay));
    }

    @Test
    public void shouldPrintFormattedDateTimeWithPredefined() {
        Assert.assertEquals("2018-03-09", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.of(2018, 3, 9)));
        Assert.assertEquals("2018-03-09-03:00", DateTimeFormatter.ISO_OFFSET_DATE.format(LocalDate.of(2018, 3, 9).atStartOfDay(ZoneId.of("UTC-3"))));
        Assert.assertEquals("Fri, 9 Mar 2018 00:00:00 -0300", DateTimeFormatter.RFC_1123_DATE_TIME.format(LocalDate.of(2018, 3, 9).atStartOfDay(ZoneId.of("UTC-3"))));
    }
}
