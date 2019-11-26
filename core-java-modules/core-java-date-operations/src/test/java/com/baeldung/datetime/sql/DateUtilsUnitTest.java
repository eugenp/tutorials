package com.baeldung.datetime.sql;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsUnitTest {

    @Test
    public void givenCurrentDate_thenTodayIsReturned() {
        assertEquals(DateUtils.getNow().toLocalDate(), LocalDate.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDateAsString_whenPatternIsNotRespected_thenExceptionIsThrown() {
        DateUtils.getDate("2020 01 01");
    }

    @Test
    public void givenDateAndPattern_thenDateIsCorrectlyReturned() throws ParseException {
        assertEquals(DateUtils.getDate("2020-01-01"), DateUtils.getDate("2020/01/01", "yyyy/MM/dd"));
    }
}