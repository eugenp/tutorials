package com.baeldung.checkiftimebetweentwotimes;

import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CheckIfTimeBetweenTwoTimesUnitTest {
    private LocalTime startTime = LocalTime.parse("09:00:00");
    private LocalTime endTime = LocalTime.parse("17:00:00");
    private LocalTime targetTime = LocalTime.parse("12:30:00");

    @Test
    public void givenLocalTime_whenUsingIsAfterIsBefore_thenTimeIsBetween() {
        assertTrue(!targetTime.isBefore(startTime) && !targetTime.isAfter(endTime));
    }

    @Test
    public void givenLocalTime_whenUsingCompareTo_thenTimeIsBetween() {
        assertTrue(targetTime.compareTo(startTime) >= 0 && targetTime.compareTo(endTime) <= 0);
    }

    @Test
    public void givenDate_whenUsingAfterBefore_thenTimeIsBetween() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, 9);
        startCalendar.set(Calendar.MINUTE, 0);
        Date startTime = startCalendar.getTime();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, 17);
        endCalendar.set(Calendar.MINUTE, 0);
        Date endTime = endCalendar.getTime();

        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.set(Calendar.HOUR_OF_DAY, 12);
        targetCalendar.set(Calendar.MINUTE, 30);
        Date targetTime = targetCalendar.getTime();

        assertTrue(!targetTime.before(startTime) && !targetTime.after(endTime));
    }
}