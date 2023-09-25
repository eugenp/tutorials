package com.baeldung.daysinmonth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysInMonthUtilsUnitTest {

    @Test
    void whenGetDaysInMonthWithYearOfMonth_thenCorrectResult() {
        assertEquals(31, new DaysInMonthUtils().getDaysInMonthWithYearOfMonth(3, 2024));
        assertEquals(30, new DaysInMonthUtils().getDaysInMonthWithYearOfMonth(11, 1999));
        assertEquals(28, new DaysInMonthUtils().getDaysInMonthWithYearOfMonth(2, 2025));
        assertEquals(29, new DaysInMonthUtils().getDaysInMonthWithYearOfMonth(2, 2004));
    }

    @Test
    void whenGetDaysInMonthWithCalendar_thenCorrectResult() {
        assertEquals(31, new DaysInMonthUtils().getDaysInMonthWithCalendar(3, 2024));
        assertEquals(30, new DaysInMonthUtils().getDaysInMonthWithCalendar(11, 1999));
        assertEquals(28, new DaysInMonthUtils().getDaysInMonthWithCalendar(2, 2025));
        assertEquals(29, new DaysInMonthUtils().getDaysInMonthWithCalendar(2, 2004));
    }

}
