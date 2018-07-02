package com.baeldung.datetime.modify;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateIncrementerTest {
    private final static String DATE_TO_INCREMENT = "2018-07-03";
    private final static String EXPECTED_DATE = "2018-07-04";

    @Test
    public void testAddOneDay() throws Exception {
        String incrementedDate = DateIncrementer.addOneDay(DATE_TO_INCREMENT);
        assertEquals(incrementedDate, EXPECTED_DATE);
    }

    @Test
    public void addOneDayJodaTime() throws Exception {
        String incrementedDate = DateIncrementer.addOneDayJodaTime(DATE_TO_INCREMENT);
        assertEquals(incrementedDate, EXPECTED_DATE);
    }
}
