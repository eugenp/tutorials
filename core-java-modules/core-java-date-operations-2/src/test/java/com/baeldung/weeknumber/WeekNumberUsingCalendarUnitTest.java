package com.baeldung.weeknumber;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class WeekNumberUsingCalendarUnitTest {

    @Test
    public void givenDateFormatAndLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() throws ParseException {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(12, calendar.getWeekNumberFrom("20200322", "yyyyMMdd", Locale.ITALY));
    }

    @Test
    public void givenDateFormatAndLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() throws ParseException {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom("20200322", "yyyyMMdd", Locale.CANADA));
    }

    @Test
    public void givenDateUsingFieldsAndLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(12, calendar.getWeekNumberFrom(2020, 2, 22, Locale.ITALY));
    }

    @Test
    public void givenDateUsingFieldsAndLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom(2020, 2, 22, Locale.CANADA));
    }

    @Test
    public void givenDateUsingFieldsAndLocaleItaly_whenChangingWeekCalcSettings_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom(2020, 2, 22, Calendar.SUNDAY, 4));
    }

}
