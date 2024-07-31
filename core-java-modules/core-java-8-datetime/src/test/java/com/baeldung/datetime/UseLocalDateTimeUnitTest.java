package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;

import org.junit.Test;

public class UseLocalDateTimeUnitTest {

    private UseLocalDateTime useLocalDateTime = new UseLocalDateTime();

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

    @Test
    public void givenLocalDateTimeInFebruary_whenRequestingMonth_thenMonthIsFebruary() {
        LocalDateTime givenLocalDateTime = LocalDateTime.of(2015, Month.FEBRUARY, 20, 6, 30);

        assertThat(givenLocalDateTime.getMonth()).isEqualTo(Month.FEBRUARY);
    }

    @Test
    public void givenLocalDateTime_whenManipulating_thenResultIsAsExpected() {
        LocalDateTime givenLocalDateTime = LocalDateTime.parse("2015-02-20T06:30:00");

        LocalDateTime manipulatedLocalDateTime = givenLocalDateTime.plusDays(1);
        manipulatedLocalDateTime = manipulatedLocalDateTime.minusHours(2);

        assertThat(manipulatedLocalDateTime).isEqualTo(LocalDateTime.of(2015, Month.FEBRUARY, 21, 4, 30));
    }

    @Test
    public void whenRequestTimeFromEpoch_thenResultIsAsExpected() {
        LocalDateTime result = useLocalDateTime.ofEpochSecond(1465817690, ZoneOffset.UTC);

        assertThat(result.toString()).isEqualTo("2016-06-13T11:34:50");
    }
}
