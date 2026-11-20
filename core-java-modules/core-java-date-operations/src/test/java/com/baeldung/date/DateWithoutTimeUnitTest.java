package com.baeldung.date;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateWithoutTimeUnitTest {

    @Test
    public void whenGettingDateWithoutTimeUsingCalendar_thenReturnDateWithoutTime() {
        Date dateWithoutTime = DateWithoutTime.getDateWithoutTimeUsingCalendar();

        // first check the time is set to 0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithoutTime);

        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));

        assertCalendarDayBoundary(calendar);
    }

    @Test
    public void whenGettingDateWithoutTimeUsingFormat_thenReturnDateWithoutTime() throws ParseException {
        Date dateWithoutTime = DateWithoutTime.getDateWithoutTimeUsingFormat();

        // first check the time is set to 0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateWithoutTime);

        assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));

        assertCalendarDayBoundary(calendar);
    }

    @Test
    public void whenAdvancingAcrossDstFallback_thenCalendarDayBoundaryIsCorrect() {
        Calendar startOfDay = bucharestAtStartOfDay(2026, Calendar.OCTOBER, 25);
        Calendar nextDay = (Calendar) startOfDay.clone();
        nextDay.add(Calendar.DATE, 1);
        assertEquals(TimeUnit.HOURS.toMillis(25),
            nextDay.getTimeInMillis() - startOfDay.getTimeInMillis());
        assertCalendarDayBoundary(startOfDay);
    }

    @Test
    public void whenAdvancingAcrossDstSpringForward_thenCalendarDayBoundaryIsCorrect() {
        Calendar startOfDay = bucharestAtStartOfDay(2026, Calendar.MARCH, 29);
        Calendar nextDay = (Calendar) startOfDay.clone();
        nextDay.add(Calendar.DATE, 1);
        assertEquals(TimeUnit.HOURS.toMillis(23),
            nextDay.getTimeInMillis() - startOfDay.getTimeInMillis());
        assertCalendarDayBoundary(startOfDay);
    }

    @Test
    public void whenGettingLocalDate_thenReturnDateWithoutTime() {
        // get the local date
        LocalDate localDate = DateWithoutTime.getLocalDate();

        // LocalDate represents a date without a time.
        assertEquals(LocalDate.now(), localDate);
    }

    private static Calendar bucharestAtStartOfDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        calendar.clear();
        calendar.set(year, month, day);
        return calendar;
    }

    private static void assertCalendarDayBoundary(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar nextDay = (Calendar) calendar.clone();
        nextDay.add(Calendar.DATE, 1);
        nextDay.add(Calendar.MILLISECOND, -1);
        assertEquals(day, nextDay.get(Calendar.DAY_OF_MONTH));
        nextDay.add(Calendar.MILLISECOND, 1);
        assertNotEquals(day, nextDay.get(Calendar.DAY_OF_MONTH));
    }

}
