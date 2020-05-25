package com.baeldung.weeknumber;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class WeekNumberUsingLocalDateUnitTest {
    @Test
    public void givenDateFormatAndLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(Integer.valueOf(12), localDate.getWeekNumberUsingWeekFiedsFrom("20200322", "yyyyMMdd", Locale.ITALY));
    }

    @Test
    public void givenDateFormatAndLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(Integer.valueOf(13), localDate.getWeekNumberUsingWeekFiedsFrom("20200322", "yyyyMMdd", Locale.CANADA));
    }

    @Test
    public void givenDateUsingChronoFields_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(Integer.valueOf(12), localDate.getWeekNumberUsingChronoFieldFrom(2020, 3, 22));
    }

    @Test
    public void givenDateUsingFieldsWithLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(Integer.valueOf(12), localDate.getWeekNumberUsingWeekFieldsFrom(2020, 3, 22, Locale.ITALY));
    }

    @Test
    public void givenDateUsingFieldsWithLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        WeekNumberUsingLocalDate localDate = new WeekNumberUsingLocalDate();

        assertEquals(Integer.valueOf(13), localDate.getWeekNumberUsingWeekFieldsFrom(2020, 3, 22, Locale.CANADA));
    }
}
