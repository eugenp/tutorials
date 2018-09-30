package com.baeldung;

import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DateDiffUnitTest {
    @Test
    public void givenTwoDatesBeforeJava8_whenDifferentiating_thenWeGetSix() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse("06/24/2017");
        Date secondDate = sdf.parse("06/30/2017");

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        assertEquals(diff, 6);
    }

    @Test
    public void givenTwoDatesInJava8_whenDifferentiating_thenWeGetSix() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime sixDaysBehind = now.minusDays(6);

        Duration duration = Duration.between(now, sixDaysBehind);
        long diff = Math.abs(duration.toDays());

        assertEquals(diff, 6);
    }

    @Test
    public void givenTwoDatesInJodaTime_whenDifferentiating_thenWeGetSix() {
        DateTime now = DateTime.now();
        DateTime sixDaysBehind = now.minusDays(6);

        org.joda.time.Duration duration = new org.joda.time.Duration(now, sixDaysBehind);
        long diff = Math.abs(duration.getStandardDays());

        assertEquals(diff, 6);
    }

    @Test
    public void givenTwoDatesInDate4j_whenDifferentiating_thenWeGetSix() {
        hirondelle.date4j.DateTime now = hirondelle.date4j.DateTime.now(TimeZone.getDefault());
        hirondelle.date4j.DateTime sixDaysBehind = now.minusDays(6);

        long diff = Math.abs(now.numDaysFrom(sixDaysBehind));

        assertEquals(diff, 6);
    }
}