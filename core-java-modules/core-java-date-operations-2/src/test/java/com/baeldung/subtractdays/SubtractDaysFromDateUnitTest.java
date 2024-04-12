package com.baeldung.subtractdays;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.junit.Test;

public class SubtractDaysFromDateUnitTest {

    @Test
    public void givenLocalDateTime_whenSubtractingFiveDays_dateIsChangedCorrectly() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 4, 20, 0, 0);

        localDateTime = localDateTime.minusDays(5);

        assertEquals(15, localDateTime.getDayOfMonth());
        assertEquals(4, localDateTime.getMonthValue());
        assertEquals(2022, localDateTime.getYear());
    }

    @Test
    public void givenCalendarDate_whenSubtractingFiveDays_dateIsChangedCorrectly() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.APRIL, 20);

        calendar.add(Calendar.DATE, -5);

        assertEquals(15, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.APRIL, calendar.get(Calendar.MONTH));
        assertEquals(2022, calendar.get(Calendar.YEAR));
    }

    @Test
    public void givenJodaDateTime_whenSubtractingFiveDays_dateIsChangedCorrectly() {
        DateTime dateTime = new DateTime(2022, 4, 20, 12, 0, 0);

        dateTime = dateTime.minusDays(5);

        assertEquals(15, dateTime.getDayOfMonth());
        assertEquals(4, dateTime.getMonthOfYear());
        assertEquals(2022, dateTime.getYear());
    }
}
