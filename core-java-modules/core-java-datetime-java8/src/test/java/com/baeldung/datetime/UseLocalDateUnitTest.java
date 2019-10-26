package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

public class UseLocalDateUnitTest {

    private UseLocalDate useLocalDate = new UseLocalDate();

    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalDate() {
        assertEquals("2016-05-10", useLocalDate.getLocalDateUsingFactoryOfMethod(2016, 5, 10)
            .toString());
    }

    @Test
    public void givenString_whenUsingParse_thenLocalDate() {
        assertEquals("2016-05-10", useLocalDate.getLocalDateUsingParseMethod("2016-05-10")
            .toString());
    }

    @Test
    public void whenUsingClock_thenLocalDate() {
        assertEquals(LocalDate.now(), useLocalDate.getLocalDateFromClock());
    }

    @Test
    public void givenDate_whenUsingPlus_thenNextDay() {
        assertEquals(LocalDate.now()
            .plusDays(1), useLocalDate.getNextDay(LocalDate.now()));
    }

    @Test
    public void givenDate_whenUsingMinus_thenPreviousDay() {
        assertEquals(LocalDate.now()
            .minusDays(1), useLocalDate.getPreviousDay(LocalDate.now()));
    }

    @Test
    public void givenToday_whenUsingGetDayOfWeek_thenDayOfWeek() {
        assertEquals(DayOfWeek.SUNDAY, useLocalDate.getDayOfWeek(LocalDate.parse("2016-05-22")));
    }

    @Test
    public void givenToday_whenUsingWithTemporalAdjuster_thenFirstDayOfMonth() {
        assertEquals(1, useLocalDate.getFirstDayOfMonth()
            .getDayOfMonth());
    }

    @Test
    public void givenLocalDate_whenUsingAtStartOfDay_thenReturnMidnight() {
        assertEquals(LocalDateTime.parse("2016-05-22T00:00:00"), useLocalDate.getStartOfDay(LocalDate.parse("2016-05-22")));
    }

    @Test
    public void givenLocalDate_whenSettingStartOfDay_thenReturnMidnightInAllCases() {
        LocalDate given = LocalDate.parse("2018-06-23");

        LocalDateTime startOfDayWithMethod = useLocalDate.getStartOfDay(given);
        LocalDateTime startOfDayOfLocalDate = useLocalDate.getStartOfDayOfLocalDate(given);
        LocalDateTime startOfDayWithMin = useLocalDate.getStartOfDayAtMinTime(given);
        LocalDateTime startOfDayWithMidnight = useLocalDate.getStartOfDayAtMidnightTime(given);

        assertThat(startOfDayWithMethod).isEqualTo(startOfDayWithMin)
            .isEqualTo(startOfDayWithMidnight)
            .isEqualTo(startOfDayOfLocalDate)
            .isEqualTo(LocalDateTime.parse("2018-06-23T00:00:00"));
        assertThat(startOfDayWithMin.toLocalTime()).isEqualTo(LocalTime.MIDNIGHT);
        assertThat(startOfDayWithMin.toString()).isEqualTo("2018-06-23T00:00");
    }

    @Test
    public void givenLocalDate_whenSettingEndOfDay_thenReturnLastMomentOfDay() {
        LocalDate given = LocalDate.parse("2018-06-23");

        LocalDateTime endOfDayWithMax = useLocalDate.getEndOfDay(given);
        LocalDateTime endOfDayFromLocalTime = useLocalDate.getEndOfDayFromLocalTime(given);

        assertThat(endOfDayWithMax).isEqualTo(endOfDayFromLocalTime);
        assertThat(endOfDayWithMax.toLocalTime()).isEqualTo(LocalTime.MAX);
        assertThat(endOfDayWithMax.toString()).isEqualTo("2018-06-23T23:59:59.999999999");
    }

    @Test
    public void givenTheYear2000_whenCheckingForLeapYear_thenReturnTrue() {
        LocalDate given = LocalDate.parse("2000-06-23");

        boolean leapYear = useLocalDate.isLeapYear(given);

        assertThat(leapYear).isEqualTo(true);
    }

    @Test
    public void givenTheYear2004_whenCheckingForLeapYear_thenReturnTrue() {
        LocalDate given = LocalDate.parse("2004-06-23");

        boolean leapYear = useLocalDate.isLeapYear(given);

        assertThat(leapYear).isEqualTo(true);
    }

    @Test
    public void givenTheYear2019_whenCheckingForLeapYear_thenReturnFalse() {
        LocalDate given = LocalDate.parse("2019-06-23");

        boolean leapYear = useLocalDate.isLeapYear(given);

        assertThat(leapYear).isEqualTo(false);
    }

}
