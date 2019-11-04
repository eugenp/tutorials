package com.baeldung.legacy.utilpackage;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class DateUtilsUnitTest {

    @Test
    public void givenTimeMillis_thenDateIsReturned() {
        Date now = DateUtils.getNow();
        assertEquals(DateUtils.getDate(now.getTime()), now);
    }

    @Test
    public void givenDateAndPattern_thenDateIsCorrectlyReturned() throws ParseException {
        long milliseconds = new Date(2020 - 1900, 0, 1).getTime();
        assertEquals(DateUtils.getDate(milliseconds), DateUtils.getDate("2020/01/01", "yyyy/MM/dd"));
    }
}