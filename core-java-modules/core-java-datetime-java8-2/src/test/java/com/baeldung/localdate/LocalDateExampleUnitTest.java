package com.baeldung.localdate;

import static org.junit.Assert.assertEquals;

import java.time.Month;

import org.junit.Test;

public class LocalDateExampleUnitTest {
    private LocalDateExample date = new LocalDateExample();

    @Test
    public void givenValues_whenUsingOfMethod_thenLocalDate() {
        assertEquals("2020-01-08", date.getCustomDateOne(2020, 1, 8));
    }

    @Test
    public void givenValuesWithMonthEnum_whenUsingOfMethod_thenLocalDate() {
        assertEquals("2020-01-08", date.getCustomDateTwo(2020, Month.JANUARY, 8));
    }

    @Test
    public void givenValues_whenUsingEpochDay_thenLocalDate() {
        assertEquals("2020-01-08", date.getDateFromEpochDay(18269));
    }

    @Test
    public void givenValues_whenUsingYearDay_thenLocalDate() {
        assertEquals("2020-01-08", date.getDateFromYearAndDayOfYear(2020, 8));
    }

    @Test
    public void givenValues_whenUsingParse_thenLocalDate() {
        assertEquals("2020-01-08", date.getDateFromString("2020-01-08"));
    }

    @Test
    public void givenValuesWithFormatter_whenUsingParse_thenLocalDate() {
        assertEquals("2020-01-08", date.getDateFromStringAndFormatter("8-Jan-2020", "d-MMM-yyyy"));
    }
}
