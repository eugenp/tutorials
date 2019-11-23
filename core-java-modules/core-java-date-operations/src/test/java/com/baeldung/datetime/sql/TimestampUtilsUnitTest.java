package com.baeldung.datetime.sql;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.datetime.sql.TimestampUtils;

import java.text.ParseException;
import java.util.Date;

public class TimestampUtilsUnitTest {

    @Test
    public void givenCurrentTimestamp_thenNowIsReturned() {
        assertEquals(TimestampUtils.getNow()
            .getTime(), new Date().getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTimestampAsString_whenPatternIsNotRespected_thenExceptionIsThrown() {
        TimestampUtils.getTimestamp("2020/01/01 10:11-12");
    }

    @Test
    public void givenTimestampAndPattern_thenTimestampIsCorrectlyReturned() throws ParseException {
        assertEquals(TimestampUtils.getTimestamp("2020-01-01 10:11:12"), TimestampUtils.getTimestamp("2020/01/01 10:11-12", "yyyy/MM/dd hh:mm-ss"));
    }
}