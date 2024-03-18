package com.baeldung.mapper;

import com.baeldung.dto.WeekDayNumber;
import com.baeldung.enums.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * The type Week day mapper test.
 */
class WeekDayMapperUnitTest {

    /**
     * To week day.
     */
    @Test
    void whenWeekDayIsMapped_thenGetDayOfWeek() {
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        WeekDay targetWeekDay = WeekDayMapper.INSTANCE.toWeekDay(dayOfWeek);
        assertEquals(WeekDay.Mon, targetWeekDay);
    }

    /**
     * String to week day.
     */
    @Test
    void whenStringIsMapped_thenGetWeekDay() {
        String dayOfWeekStr = DayOfWeek.MONDAY.name();
        WeekDay targetWeekDay = WeekDayMapper.INSTANCE.stringToWeekDay(dayOfWeekStr);
        assertEquals(WeekDay.Mon, targetWeekDay);
    }

    /**
     * String to unmapped week day.
     */
    @Test
    void whenStringIsUnmapped_thenGetException() {
        String dayOfWeekStr = "MON";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            WeekDayMapper.INSTANCE.stringToWeekDay(dayOfWeekStr);
        });
        assertEquals("Unexpected enum constant: " + dayOfWeekStr, exception.getMessage());
    }

    /**
     * Week day to string.
     */
    @Test
    void whenWeekDayIsMapped_thenGetString() {
        WeekDay weekDay = WeekDay.Mon;
        String targetWeekDayStr = WeekDayMapper.INSTANCE.weekDayToString(weekDay);
        assertEquals("MONDAY", targetWeekDayStr);
    }

    /**
     * Week day to int.
     *
     * @param weekDay            the week day
     * @param expectedWeekNumber the expected week number
     */
    @ParameterizedTest
    @CsvSource({"Mon,1", "Fri,5"})
    void whenWeekDayIsMapped_thenGetInt(WeekDay weekDay, int expectedWeekNumber) {
        Integer targetWeekDayInt = WeekDayMapper.INSTANCE.convertWeekDayToInteger(weekDay);
        WeekDayNumber targetWeekDayNumber = WeekDayMapper.INSTANCE.weekDayToWeekDayNumber(weekDay);
        assertEquals(expectedWeekNumber, targetWeekDayInt.intValue());
        assertEquals(expectedWeekNumber, targetWeekDayNumber.getNumber().intValue());
    }

    /**
     * Week day to working day.
     *
     * @param weekDay             the week day
     * @param expectedWorkWeekDay the expected work week day
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon", "Fri,Fri", "Sat,Mon"})
    void whenWeekDayIsMapped_thenGetWorkWeekDay(WeekDay weekDay, WorkWeekDay expectedWorkWeekDay) {
        WorkWeekDay targetWorkWeekDay = WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithRemaining(weekDay);
        assertEquals(expectedWorkWeekDay, targetWorkWeekDay);
    }

    /**
     * Week day to working day.
     *
     * @param weekDay             the week day
     * @param expectedWorkWeekDay the expected work week day
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon", "Fri,Fri", "Sat,Mon"})
    void whenWeekDayIsMappedWithRemaining_thenGetWorkWeekDay(WeekDay weekDay, WorkWeekDay expectedWorkWeekDay) {
        WorkWeekDay targetWorkWeekDay = WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithRemaining(weekDay);
        assertEquals(expectedWorkWeekDay, targetWorkWeekDay);
    }

    /**
     * Week day to working day.
     *
     * @param weekDay             the week day
     * @param expectedWorkWeekDay the expected work week day
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon", "Fri,Fri", "Sat,Mon"})
    void whenWeekDayIsMappedWithUnmapped_thenGetWorkWeekDay(WeekDay weekDay, WorkWeekDay expectedWorkWeekDay) {
        WorkWeekDay targetWorkWeekDay = WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithUnmapped(weekDay);
        assertEquals(expectedWorkWeekDay, targetWorkWeekDay);
    }

    /**
     * Week day to working day with null handling.
     *
     * @param weekDay             the week day
     * @param expectedWorkWeekDay the expected work week day
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon", "Tue,", "Sat,", ",Wed"})
    void whenWeekDayIsMappedWithNull_thenGetWorkWeekDay(WeekDay weekDay, WorkWeekDay expectedWorkWeekDay) {
        WorkWeekDay targetWorkWeekDay = WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithNullHandling(weekDay);
        assertEquals(expectedWorkWeekDay, targetWorkWeekDay);
    }

    /**
     * Week day to work week day with exception handling.
     *
     * @param weekDay             the week day
     * @param expectedWorkWeekDay the expected work week day
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon", "Fri,Fri", "Sat,", "Sun,"})
    void whenWeekDayIsMappedWithException_thenGetWorkWeekDay(WeekDay weekDay, WorkWeekDay expectedWorkWeekDay) {
        if (weekDay == WeekDay.Sat || weekDay == WeekDay.Sun) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithExceptionHandling(weekDay);
            });
            assertEquals("Unexpected enum constant: " + weekDay.name(), exception.getMessage());
        } else {
            WorkWeekDay targetWorkWeekDay = WeekDayMapper.INSTANCE.weekDayToWorkWeekDayWithExceptionHandling(weekDay);
            assertEquals(expectedWorkWeekDay, targetWorkWeekDay);
        }
    }

    /**
     * Apply suffix.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Mon,Mon_Value", "Fri,Fri_Value"})
    void whenWeekDayIsMappedWithSuffix_thenGetWeekDaySuffixed(WeekDay weekDay, WeekDaySuffixed expectedResult) {
        WeekDaySuffixed result = WeekDayMapper.INSTANCE.applySuffix(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Apply prefix.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Mon,Value_Mon", "Fri,Value_Fri"})
    void whenWeekDayIsMappedWithPrefix_thenGetWeekDayPrefixed(WeekDay weekDay, WeekDayPrefixed expectedResult) {
        WeekDayPrefixed result = WeekDayMapper.INSTANCE.applyPrefix(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Strip suffix.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Mon_Value,Mon", "Fri_Value,Fri"})
    void whenWeekDaySuffixedMappedWithStripped_thenGetWeekDay(WeekDaySuffixed weekDay, WeekDay expectedResult) {
        WeekDay result = WeekDayMapper.INSTANCE.stripSuffix(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Strip prefix.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Value_Mon,Mon", "Value_Fri,Fri"})
    void whenWeekDayPrefixedMappedWithStripped_thenGetWeekDay(WeekDayPrefixed weekDay, WeekDay expectedResult) {
        WeekDay result = WeekDayMapper.INSTANCE.stripPrefix(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Apply lowercase.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Mon,mon", "Fri,fri"})
    void whenWeekDayMappedWithLower_thenGetWeekDayLowercase(WeekDay weekDay, WeekDayLowercase expectedResult) {
        WeekDayLowercase result = WeekDayMapper.INSTANCE.applyLowercase(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Apply uppercase.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"Mon,MON", "Fri,FRI"})
    void whenWeekDayMappedWithUpper_thenGetWeekDayUppercase(WeekDay weekDay, WeekDayUppercase expectedResult) {
        WeekDayUppercase result = WeekDayMapper.INSTANCE.applyUppercase(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Underscore tp capital.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"MONDAY_VALUE,Monday_Value", "FRIDAY_VALUE,Friday_Value"})
    void whenWeekDayUnderscoreMappedWithCapital_thenGetStringCapital(WeekDayUnderscore weekDay, String expectedResult) {
        String result = WeekDayMapper.INSTANCE.underscoreToCapital(weekDay);
        assertEquals(expectedResult, result);
    }

    /**
     * Lowercase to capital.
     *
     * @param weekDay        the week day
     * @param expectedResult the expected result
     */
    @ParameterizedTest
    @CsvSource({"mon,Mon", "fri,Fri"})
    void whenWeekDayLowercaseMappedWithCapital_thenGetWeekDay(WeekDayLowercase weekDay, WeekDay expectedResult) {
        WeekDay result = WeekDayMapper.INSTANCE.lowercaseToCapital(weekDay);
        assertEquals(expectedResult, result);
    }
}