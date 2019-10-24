package com.baeldung.sql;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class TimeUtilsUnitTest {

    @Test
    public void givenCurrentTime_thenNowIsReturned() {
        assertEquals(TimeUtils.getNow(), new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTimeAsString_whenPatternIsNotRespected_thenExceptionIsThrown() {
        TimeUtils.getTime("10 11 12");
    }

    @Test
    public void givenTimeAndPattern_thenTimeIsCorrectlyReturned() throws ParseException {
        assertEquals(TimeUtils.getTime("10:11:12"), TimeUtils.getTime("10 11 12", "hh mm ss"));
    }
}