package com.baeldung.legacy.utilpackage;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class CalendarUtilsUnitTest {

    @Test
    public void givenDateAndDaysToAdd_thenCalendarIsCorrectlyReturned() throws ParseException {
        Date initialDate = DateUtils.getDate("2020/01/01", "yyyy/MM/dd");
        Date expectedDate= DateUtils.getDate("2020/01/11", "yyyy/MM/dd");
        assertEquals(expectedDate, CalendarUtils.getPlusDays(initialDate, 10).getTime());
    }
}