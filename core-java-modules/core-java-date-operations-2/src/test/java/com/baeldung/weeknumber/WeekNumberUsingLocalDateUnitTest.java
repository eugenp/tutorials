package com.baeldung.weeknumber;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class WeekNumberUsingLocalDateUnitTest {
    @Test
    public void givenDateInStringAndDateFormatUsingWeekFieldsWithLocaleItaly_thenGettingWeekNumberUsingLocalDateIsCorrectlyReturned() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(12, localDate.getWeekNumberUsingWeekFiedsFrom("20200322", "yyyyMMdd", Locale.ITALY)
            .longValue());
    }

    @Test
    public void givenDateInStringAndDateFormatUsingWeekFieldsWithLocaleCanada_thenGettingWeekNumberUsingLocalDateIsCorrectlyReturned() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(13, localDate.getWeekNumberUsingWeekFiedsFrom("20200322", "yyyyMMdd", Locale.CANADA)
            .longValue());
    }

    @Test
    public void givenDateInStringAndDateFormatUsingChronoFieds_thenGettingWeekNumberUsingLocalDateIsCorrectlyReturned() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(12, localDate.getWeekNumberUsingChronoFieldFrom(2020, 3, 22)
            .longValue());
    }

    @Test
    public void givenDateInYearMonthDayNumbersUsingWeekFieldsWithLocaleItaly_thenGettingWeekNumberUsingLocalDateIsCorrectlyReturned() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(12, localDate.getWeekNumberUsinWeekFieldsFrom(2020, 3, 22, Locale.ITALY)
            .longValue());
    }

    @Test
    public void givenDateInYearMonthDayNumbersUsingWeekFieldsWithLocaleCanada_thenGettingWeekNumberUsingLocalDateIsCorrectlyReturned() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(13, localDate.getWeekNumberUsinWeekFieldsFrom(2020, 3, 22, Locale.CANADA)
            .longValue());
    }
}
