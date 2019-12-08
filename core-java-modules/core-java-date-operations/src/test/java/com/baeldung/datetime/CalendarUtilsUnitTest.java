package com.baeldung.datetime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.datetime.CalendarUtils;
import com.baeldung.datetime.DateUtils;

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