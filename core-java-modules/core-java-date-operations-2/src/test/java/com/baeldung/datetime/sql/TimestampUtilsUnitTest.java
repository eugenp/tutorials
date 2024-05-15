package com.baeldung.datetime.sql;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class TimestampUtilsUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void givenTimestampAsString_whenPatternIsNotRespected_thenExceptionIsThrown() {
        TimestampUtils.getTimestamp("2020/01/01 10:11-12");
    }

    @Test
    public void givenTimestampAndPattern_thenTimestampIsCorrectlyReturned() throws ParseException {
        assertEquals(TimestampUtils.getTimestamp("2020-01-01 10:11:12"), TimestampUtils.getTimestamp("2020/01/01 10:11-12", "yyyy/MM/dd hh:mm-ss"));
    }
}