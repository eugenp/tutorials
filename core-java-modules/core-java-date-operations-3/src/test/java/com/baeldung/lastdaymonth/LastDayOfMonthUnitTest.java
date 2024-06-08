package com.baeldung.lastdaymonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;

class LastDayOfMonthUnitTest {

    @Test
    void givenMonth_whenUsingCalendar_thenReturnLastDay() {
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingCalendar(0));
        assertEquals(30, LastDayOfMonth.getLastDayOfMonthUsingCalendar(3));
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingCalendar(9));
    }

    @Test
    void givenMonth_whenUsingTemporalAdjusters_thenReturnLastDay() {
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingTemporalAdjusters(LocalDate.of(2023, 1, 1)));
        assertEquals(30, LastDayOfMonth.getLastDayOfMonthUsingTemporalAdjusters(LocalDate.of(2023, 4, 1)));
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingTemporalAdjusters(LocalDate.of(2023, 10, 1)));
    }

    @Test
    void givenMonth_whenUsingYearMonth_thenReturnLastDay() {
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingYearMonth(YearMonth.of(2023, 1)));
        assertEquals(30, LastDayOfMonth.getLastDayOfMonthUsingYearMonth(YearMonth.of(2023, 4)));
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingYearMonth(YearMonth.of(2023, 10)));
    }

    @Test
    void givenMonth_whenUsingJodaTime_thenReturnLastDay() {
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingJodaTime(org.joda.time.LocalDate.parse("2023-1-1")));
        assertEquals(30, LastDayOfMonth.getLastDayOfMonthUsingJodaTime(org.joda.time.LocalDate.parse("2023-4-1")));
        assertEquals(31, LastDayOfMonth.getLastDayOfMonthUsingJodaTime(org.joda.time.LocalDate.parse("2023-10-1")));
    }

}
