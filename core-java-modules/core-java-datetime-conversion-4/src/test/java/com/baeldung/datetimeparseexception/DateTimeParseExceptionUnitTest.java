package com.baeldung.datetimeparseexception;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeParseExceptionUnitTest {

    // Failing Test: Trying to obtain LocalDateTime from a date without time
    @Test
    void givenDateWithoutTime_whenParsedAsLocalDateTime_thenThrowsDateTimeParseException() {
        String dateStr = "2024-03-25";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        TemporalAccessor parsedDate = formatter.parse(dateStr);

        DateTimeException exception = assertThrows(DateTimeException.class, () ->
                LocalDateTime.from(parsedDate)
        );

        assertTrue(exception.getMessage().contains("Unable to obtain LocalDateTime from TemporalAccessor"));
    }

    // Fixed Version: Correctly using LocalDate
    @Test
    void givenDateWithoutTime_whenParsedAsLocalDate_thenSucceeds() {
        String dateStr = "2024-03-25";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse(dateStr, formatter);

        assertEquals(LocalDate.of(2024, 3, 25), date);
    }

    // Fixed Version: Correctly using LocalDateTime with default time
    @Test
    void givenDateWithTime_whenParsedAsLocalDateTime_thenSucceeds() {
        String dateTimeStr = "2024-03-25T00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        assertEquals(LocalDateTime.of(2024, 3, 25, 0, 0, 0), dateTime);
    }

    // Failing Test: Trying to obtain LocalDateTime from DayOfWeek
    @Test
    void givenDayOfWeek_whenParsedAsLocalDateTime_thenThrowsDateTimeParseException() {
        TemporalAccessor parsedDate = DayOfWeek.FRIDAY;

        DateTimeException exception = assertThrows(DateTimeException.class, () ->
                LocalDateTime.from(parsedDate)
        );

        assertTrue(exception.getMessage().contains("Unable to obtain LocalDateTime from TemporalAccessor"));
    }

    // Fixed Version: Correctly using DayOfWeek
    @Test
    void givenDayOfWeek_whenUsedAsIs_thenSucceeds() {
        DayOfWeek day = DayOfWeek.FRIDAY;

        assertEquals(DayOfWeek.FRIDAY, day);
    }

    // Fixed Version: Combine DayOfWeek with LocalDate and LocalTime
    @Test
    void givenDayOfWeek_whenCombinedWithLocalDateAndLocalTime_thenSucceeds() {
        LocalDate date = LocalDate.of(2024, 3, 25); // Specific date
        LocalTime time = LocalTime.of(14, 30);      // Set a specific time
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertEquals(LocalDateTime.of(2024, 3, 25, 14, 30), dateTime);
    }

    // Failing Test: Trying to obtain LocalDateTime from LocalTime
    @Test
    void givenLocalTime_whenParsedAsLocalDateTime_thenThrowsDateTimeParseException() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        TemporalAccessor parsedDate = formatter.parse("14:30:00");

        DateTimeException exception = assertThrows(DateTimeException.class, () ->
                LocalDateTime.from(parsedDate)
        );

        assertTrue(exception.getMessage().contains("Unable to obtain LocalDateTime from TemporalAccessor"));
    }

    // Fixed Version: Combine LocalTime with LocalDate to form a valid LocalDateTime
    @Test
    void givenLocalTime_whenCombinedWithLocalDate_thenSucceeds() {
        LocalDate date = LocalDate.of(2024, 3, 25);
        LocalTime time = LocalTime.parse("14:30:00");
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertNotNull(dateTime);
        assertEquals(LocalDateTime.of(2024, 3, 25, 14, 30), dateTime);
    }

    // Failing Test: Trying to obtain LocalDateTime from YearMonth
    @Test
    void givenYearMonth_whenParsedAsLocalDateTime_thenThrowsDateTimeParseException() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        TemporalAccessor parsedDate = formatter.parse("2024-03");

        DateTimeException exception = assertThrows(DateTimeException.class, () ->
                LocalDateTime.from(parsedDate)
        );

        assertTrue(exception.getMessage().contains("Unable to obtain LocalDateTime from TemporalAccessor"));
    }

    // Fixed Version: Correctly using YearMonth
    @Test
    void givenYearMonth_whenParsedCorrectly_thenSucceeds() {
        YearMonth yearMonth = YearMonth.parse("2024-03", DateTimeFormatter.ofPattern("yyyy-MM"));
        assertEquals(YearMonth.of(2024, 3), yearMonth);
    }

    // Failing Test: Trying to obtain LocalDateTime from MonthDay
    @Test
    void givenMonthDay_whenParsedAsLocalDateTime_thenThrowsDateTimeParseException() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        TemporalAccessor parsedDate = formatter.parse("03-25");

        DateTimeException exception = assertThrows(DateTimeException.class, () ->
                LocalDateTime.from(parsedDate)
        );

        assertTrue(exception.getMessage().contains("Unable to obtain LocalDateTime from TemporalAccessor"));
    }

    // Fixed Version: Correctly using MonthDay
    @Test
    void givenMonthDay_whenParsedCorrectly_thenSucceeds() {
        MonthDay monthDay = MonthDay.parse("03-25", DateTimeFormatter.ofPattern("MM-dd"));
        assertEquals(MonthDay.of(3, 25), monthDay);
    }

    // Fixed Version: Combine MonthDay with LocalDate and LocalTime
    @Test
    void givenMonthDay_whenCombinedWithYearAndTime_thenSucceeds() {
        MonthDay monthDay = MonthDay.parse("03-25", DateTimeFormatter.ofPattern("MM-dd"));
        LocalDate date = LocalDate.of(2024, monthDay.getMonth(), monthDay.getDayOfMonth());
        LocalTime time = LocalTime.of(14, 30);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        assertNotNull(dateTime);
        assertEquals(LocalDateTime.of(2024, 3, 25, 14, 30), dateTime);
    }
}