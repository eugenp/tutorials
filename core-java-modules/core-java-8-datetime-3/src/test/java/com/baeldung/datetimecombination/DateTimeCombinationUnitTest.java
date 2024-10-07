package com.baeldung.datetimecombination;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeCombinationUnitTest {
    LocalDate date = LocalDate.of(2023, 9, 12);
    LocalTime time = LocalTime.of(14, 30);
    LocalDateTime expectedDateTime = LocalDateTime.parse("2023-09-12T14:30");

    @Test
    public void givenDateAndTime_whenUsingLocalDateTimeOf_thenCombined() {
        LocalDateTime combinedDateTime = LocalDateTime.of(date, time);

        assertEquals(expectedDateTime, combinedDateTime);
    }

    @Test
    public void givenDateAndTime_whenUsingAtTime_thenCombined() {
        LocalDateTime combinedDateTime = date.atTime(time);

        assertEquals(expectedDateTime, combinedDateTime);
    }

    @Test
    public void givenDateAndTime_whenAdjustingTime_thenCombined() {
        LocalDate date = LocalDate.of(2024, 9, 18);
        LocalTime originalTime = LocalTime.of(14, 45);
    
        LocalTime adjustedTime = originalTime.with(ChronoField.MINUTE_OF_HOUR, 0);
        LocalDateTime expectedAdjustedDateTime = LocalDateTime.of(date, adjustedTime);
    
        LocalDateTime actualDateTime = date.atTime(adjustedTime);
    
        assertEquals(expectedAdjustedDateTime, actualDateTime);
    }
}
