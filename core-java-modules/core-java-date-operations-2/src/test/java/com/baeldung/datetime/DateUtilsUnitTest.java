package com.baeldung.datetime;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilsUnitTest {

    @Test
    public void givenDateAndPattern_thenDateIsCorrectlyReturned() throws ParseException {
        long milliseconds = new Date(2020 - 1900, 0, 1).getTime();
        assertEquals(DateUtils.getDate(milliseconds), DateUtils.getDate("2020/01/01", "yyyy/MM/dd"));
    }
}