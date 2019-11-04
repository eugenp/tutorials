package com.baeldung.legacy.sqlpackage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateUtilsUnitTest {

    @Test
    public void givenCurrentDate_thenTodayIsReturned() {
        assertEquals(DateUtils.getNow(), new Date());
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