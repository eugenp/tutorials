package com.baeldung.weeknumber;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class WeekNumberUsingCalendarUnitTest {
    @Test
    public void givenDateInStringAndDateFormatUsingLocaleItaly_thenGettingWeekNumberUsingCalendarIsCorrectlyReturned() throws ParseException {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(12, calendar.getWeekNumberFrom("20200322", "yyyyMMdd", Locale.ITALY));
    }

    @Test
    public void givenDateInStringAndDateFormatUsingLocaleCanada_thenGettingWeekNumberUsingCalendarIsCorrectlyReturned() throws ParseException {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom("20200322", "yyyyMMdd", Locale.CANADA));
    }

    @Test
    public void givenDateInYearMonthDayNumbersLocaleItaly_thenGettingWeekNumberUsingCalendarIsCorrectlyReturned() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(12, calendar.getWeekNumberFrom(2020, 2, 22, Locale.ITALY));
    }

    @Test
    public void givenDateInYearMonthDayNumbersLocaleItalyChangingWeekCalculationSettings_thenGettingWeekNumberUsingCalendarIsCorrectlyReturned() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom(2020, 2, 22, Calendar.SUNDAY, 4, Locale.ITALY));
    }

    @Test
    public void givenDateInYearMonthDayNumbersLocaleCanada_thenGettingWeekNumberUsingCalendarIsCorrectlyReturned() {
        WeekNumberUsingCalendar calendar = new WeekNumberUsingCalendar();

        assertEquals(13, calendar.getWeekNumberFrom(2020, 2, 22, Locale.CANADA));
    }
}
