package com.baeldung.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public void givenTwoDateTimesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixMinutesBehind = now.minusMinutes(6);

        Duration duration = Duration.between(now, sixMinutesBehind);
        long diff = Math.abs(duration.toMinutes());

        assertEquals(diff, 6);
    }

    @Test
    public void givenTwoDatesInJodaTime_whenDifferentiating_thenWeGetSix() {
        org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
        org.joda.time.LocalDate sixDaysBehind = now.minusDays(6);

        org.joda.time.Period period = new org.joda.time.Period(now, sixDaysBehind);
        long diff = Math.abs(period.getDays());

        assertEquals(diff, 6);
    }

    @Test
    public void givenTwoDateTimesInJodaTime_whenDifferentiating_thenWeGetSix() {
        org.joda.time.LocalDateTime now = org.joda.time.LocalDateTime.now();
        org.joda.time.LocalDateTime sixMinutesBehind = now.minusMinutes(6);

        org.joda.time.Period period = new org.joda.time.Period(now, sixMinutesBehind);
        long diff = Math.abs(period.getDays());
    }

    @Test
    public void givenTwoDatesInDate4j_whenDifferentiating_thenWeGetSix() {
        hirondelle.date4j.DateTime now = hirondelle.date4j.DateTime.now(TimeZone.getDefault());
        hirondelle.date4j.DateTime sixDaysBehind = now.minusDays(6);

        long diff = Math.abs(now.numDaysFrom(sixDaysBehind));

        assertEquals(diff, 6);
    }
}