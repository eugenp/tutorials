package com.baeldung.firstandlastdayofyear;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class FirstAndLastDayOfYearUnitTest {

    @Test
    public void givenCurrentDate_whenGettingFirstAndLastDayOfYear_thenCorrectDatesReturned() {
        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.with(firstDayOfYear());
        LocalDate lastDay = today.with(lastDayOfYear());

        assertEquals("2023-01-01", firstDay.toString());
        assertEquals("2023-12-31", lastDay.toString());
    }

    @Test
    public void givenCalendarSetToFirstDayOfYear_whenFormattingDateToISO8601_thenFormattedDateMatchesFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date firstDay = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(firstDay);

        assertEquals("2023-01-01", formattedDate);
    }

    @Test
    public void givenCalendarSetToFirstDayOfYear_whenFormattingDateToISO8601_thenFormattedDateMatchesLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date lastDay = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(lastDay);

        assertEquals("2023-12-31", formattedDate);
    }

}
