package com.baeldung.internationalization;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
}
