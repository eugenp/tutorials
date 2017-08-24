package com.baeldung;

import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateDiffTest {
    @Test
    public void givenTwoDatesBeforeJava8SeparatedBySixDays_whenCallingDiffOnThem_thenWeGetSix() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse("06/24/2017");
        Date secondDate = sdf.parse("06/30/2017");

        DateDiff dateDiff = new DateDiff();
        long diff = dateDiff.beforeJava8diff(firstDate, secondDate);

        assertEquals(diff, 6);
    }

    @Test
    public void givenTheCurrentDateAndSixDaysBehindInJava8_whenCallingDiffOnThem_thenWeGetSix() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime sixDaysBehind = now.minusDays(6);

        DateDiff dateDiff = new DateDiff();
        long diff = dateDiff.fromJava8Diff(now, sixDaysBehind);

        assertEquals(diff, 6);
    }

    @Test
    public void givenTheCurrentDateAndSixDaysBehindInJodaTime_whenCallingDiffOnThem_thenWeGetSix() {
        DateTime now = DateTime.now();
        DateTime sixDaysBehind = now.minusDays(6);

        DateDiff dateDiff = new DateDiff();
        long diff = dateDiff.fromJodaTime(now, sixDaysBehind);

        assertEquals(diff, 6);
    }

    @Test
    public void givenTheCurrentDateAndSixDaysBehindInDate4j_whenCallingDiffOnThem_thenWeGetSix() {
        hirondelle.date4j.DateTime now = hirondelle.date4j.DateTime.now(TimeZone.getDefault());
        hirondelle.date4j.DateTime sixDaysBehind = now.minusDays(6);

        DateDiff dateDiff = new DateDiff();
        long diff = dateDiff.fromDate4j(now, sixDaysBehind);

        assertEquals(diff, 6);
    }
}