package com.baeldung.rounddate;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateRoundingUnitTest {

    @Test
    public void givenDate_whenRoundToDay_thenBeginningOfDay() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 30);
        Date result = RoundDate.roundToDay(date);
        assertEquals(RoundDate.getDate(2023, Calendar.JANUARY, 27, 0, 0), result);
    }

    @Test
    public void givenDate_whenRoundToNearestHour_thenNearestHour() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 30);
        Date result = RoundDate.roundToNearestHour(date);
        assertEquals(RoundDate.getDate(2023, Calendar.JANUARY, 27, 15, 30), result);

    }

    @Test
    public void givenDate_whenRoundToStartOfMonth_thenBeginningOfMonth() {
        Date date = RoundDate.getDate(2023, Calendar.FEBRUARY, 15, 10, 45);
        Date result = RoundDate.roundToStartOfMonth(date);
        assertEquals(RoundDate.getDate(2023, Calendar.FEBRUARY, 1, 0, 0), result);
    }

    @Test
    public void givenDate_whenRoundToEndOfWeek_thenEndOfWeek() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 30);
        Date result = RoundDate.roundToEndOfWeek(date);

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(RoundDate.getDate(2023, Calendar.JANUARY, 28, 23, 59));
        expectedCalendar.set(Calendar.SECOND, 59);
        expectedCalendar.set(Calendar.MILLISECOND, 0);

        Date expected = expectedCalendar.getTime();

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void givenDate_whenRoundToNearestQuarterHour_thenNearestQuarterHour() {
        Date date = RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 12);
        Date result = RoundDate.roundToNearestQuarterHour(date);
        assertEquals(RoundDate.getDate(2023, Calendar.JANUARY, 27, 14, 15), result);
    }
}
