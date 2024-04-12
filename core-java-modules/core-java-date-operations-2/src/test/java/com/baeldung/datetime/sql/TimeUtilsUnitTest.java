package com.baeldung.datetime.sql;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.datetime.sql.TimeUtils;

import java.text.ParseException;

public class TimeUtilsUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void givenTimeAsString_whenPatternIsNotRespected_thenExceptionIsThrown() {
        TimeUtils.getTime("10 11 12");
    }

    @Test
    public void givenTimeAndPattern_thenTimeIsCorrectlyReturned() throws ParseException {
        assertEquals(TimeUtils.getTime("10:11:12"), TimeUtils.getTime("10 11 12", "hh mm ss"));
    }
}