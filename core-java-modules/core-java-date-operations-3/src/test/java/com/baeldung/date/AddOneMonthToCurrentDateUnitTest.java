package com.baeldung.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

class AddOneMonthToCurrentDateUnitTest {

    @Test
    void givenCalendarDate_whenAddingOneMonth_thenDateIsChangedCorrectly() {
        Calendar calendar = Calendar.getInstance();
        // Dummy current date
        calendar.set(2023, Calendar.APRIL, 20);

        // add one month
        calendar.add(Calendar.MONTH, 1);

        assertEquals(Calendar.MAY, calendar.get(Calendar.MONTH));
        assertEquals(20, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(2023, calendar.get(Calendar.YEAR));
    }

    @SuppressWarnings("deprecation")
    @Test
    void givenDate_whenAddingOneMonth_thenDateIsChangedCorrectly() {
        // Dummy current date
        Date currentDate = new Date(2023, Calendar.DECEMBER, 20);
        // Expected Date
        Date expectedDate = new Date(2024, Calendar.JANUARY, 20);

        // add one month
        currentDate.setMonth(currentDate.getMonth() + 1);

        assertEquals(expectedDate, currentDate);
    }

    @Test
    void givenJavaLocalDate_whenAddingOneMonth_thenDateIsChangedCorrectly() {
        // Dummy current date
        LocalDate localDate = LocalDate.of(2023, 12, 20);

        // add one month
        localDate = localDate.plusMonths(1);

        assertEquals(1, localDate.getMonthValue());
        assertEquals(20, localDate.getDayOfMonth());
        assertEquals(2024, localDate.getYear());
    }

    @Test
    void givenJodaTimeLocalDate_whenAddingOneMonth_thenDateIsChangedCorrectly() {
        // Dummy current date
        org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(2023, 12, 20);

        // add one month
        localDate = localDate.plusMonths(1);

        assertEquals(1, localDate.getMonthOfYear());
        assertEquals(20, localDate.getDayOfMonth());
        assertEquals(2024, localDate.getYear());
    }

    @Test
    void givenApacheCommonsLangDateUtils_whenAddingOneMonth_thenDateIsChangedCorrectly() {
        // Dummy current date
        Date currentDate = new GregorianCalendar(2023, Calendar.APRIL, 20, 4, 0).getTime();
        // Expected Date
        Date expectedDate = new GregorianCalendar(2023, Calendar.MAY, 20, 4, 0).getTime();

        assertEquals(expectedDate, DateUtils.addMonths(currentDate, 1));
    }

}
