package com.baeldung.weeknumber;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class GetWeekNumberUnitTest {

    @Test
    public void givenDateUsingFieldsAndLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        Calendar calendar = Calendar.getInstance(Locale.ITALY);
        calendar.set(2020, 10, 22);

        assertEquals(47, calendar.get(Calendar.WEEK_OF_YEAR));
    }

    @Test
    public void givenDateUsingFieldsAndLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        Calendar calendar = Calendar.getInstance(Locale.CANADA);
        calendar.set(2020, 10, 22);

        assertEquals(48, calendar.get(Calendar.WEEK_OF_YEAR));
    }

    @Test
    public void givenDateUsingFieldsAndLocaleItaly_whenChangingWeekCalcSettings_thenWeekIsReturnedCorrectly() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.set(2020, 2, 22);

        assertEquals(13, calendar.get(Calendar.WEEK_OF_YEAR));
    }

    @Test
    public void givenDateUsingChronoFields_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        LocalDate date = LocalDate.of(2020, 3, 22);

        assertEquals(12, date.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
    }

    @Test
    public void givenDateUsingFieldsWithLocaleItaly_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        LocalDate date = LocalDate.of(2020, 3, 22);

        assertEquals(12, date.get(WeekFields.of(Locale.ITALY)
            .weekOfYear()));
    }

    @Test
    public void givenDateUsingFieldsWithLocaleCanada_whenGetWeekNumber_thenWeekIsReturnedCorrectly() {
        LocalDate date = LocalDate.of(2020, 3, 22);

        assertEquals(13, date.get(WeekFields.of(Locale.CANADA)
            .weekOfYear()));
    }

}
