package com.baeldung.datetime.modify;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateIncrementerUnitTest {
    private final static String DATE_TO_INCREMENT = "2018-07-03";
    private final static String EXPECTED_DATE = "2018-07-04";

    @Test
    public void givenDate_whenUsingJava8_thenAddOneDay() throws Exception {
        String incrementedDate = DateIncrementer.addOneDay(DATE_TO_INCREMENT);
        assertEquals(EXPECTED_DATE, incrementedDate);
    }

    @Test
    public void givenDate_whenUsingJodaTime_thenAddOneDay() throws Exception {
        String incrementedDate = DateIncrementer.addOneDayJodaTime(DATE_TO_INCREMENT);
        assertEquals(EXPECTED_DATE, incrementedDate);
    }

    @Test
    public void givenDate_whenUsingCalendar_thenAddOneDay() throws Exception {
        String incrementedDate = DateIncrementer.addOneDayCalendar(DATE_TO_INCREMENT);
        assertEquals(EXPECTED_DATE, incrementedDate);
    }

    @Test
    public void givenDate_whenUsingApacheCommons_thenAddOneDay() throws Exception {
        String incrementedDate = DateIncrementer.addOneDayApacheCommons(DATE_TO_INCREMENT);
        assertEquals(EXPECTED_DATE, incrementedDate);
    }
}
