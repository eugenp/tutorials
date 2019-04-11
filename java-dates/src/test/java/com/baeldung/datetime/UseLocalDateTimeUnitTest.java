package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import org.junit.Test;

public class UseLocalDateTimeUnitTest {

    UseLocalDateTime useLocalDateTime = new UseLocalDateTime();

    @Test
    public void givenString_whenUsingParse_thenLocalDateTime() {
        assertEquals(LocalDate.of(2016, Month.MAY, 10), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30")
            .toLocalDate());
        assertEquals(LocalTime.of(6, 30), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30")
            .toLocalTime());
    }

    @Test
    public void givenLocalDateTime_whenSettingEndOfDay_thenReturnLastMomentOfDay() {
        LocalDateTime givenTimed = LocalDateTime.parse("2018-06-23T05:55:55");

        LocalDateTime endOfDayFromGivenDirectly = useLocalDateTime.getEndOfDayFromLocalDateTimeDirectly(givenTimed);
        LocalDateTime endOfDayFromGiven = useLocalDateTime.getEndOfDayFromLocalDateTime(givenTimed);

        assertThat(endOfDayFromGivenDirectly).isEqualTo(endOfDayFromGiven);
        assertThat(endOfDayFromGivenDirectly.toLocalTime()).isEqualTo(LocalTime.MAX);
        assertThat(endOfDayFromGivenDirectly.toString()).isEqualTo("2018-06-23T23:59:59.999999999");
    }
}
