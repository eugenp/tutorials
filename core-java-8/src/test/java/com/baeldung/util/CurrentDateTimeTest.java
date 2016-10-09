package com.baeldung.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class CurrentDateTimeTest {

    private static Clock clock;

    @BeforeClass
    public static void setup() {
        final Instant currentTime = Instant.parse("2016-10-09T15:10:30.00Z");

        clock = Mockito.mock(Clock.class);
        when(clock.instant()).thenAnswer((invocation) -> currentTime);
        when(clock.getZone()).thenAnswer((invocation) -> ZoneId.of("UTC"));
    }

    @Test
    public void shouldReturnCurrentDate() {

        final LocalDate now = LocalDate.now(clock);

        assertEquals(9, now.get(ChronoField.DAY_OF_MONTH));
        assertEquals(10, now.get(ChronoField.MONTH_OF_YEAR));
        assertEquals(2016, now.get(ChronoField.YEAR));
    }

    @Test
    public void shouldReturnCurrentTime() {

        final LocalTime now = LocalTime.now(clock);

        assertEquals(15, now.get(ChronoField.HOUR_OF_DAY));
        assertEquals(10, now.get(ChronoField.MINUTE_OF_HOUR));
        assertEquals(30, now.get(ChronoField.SECOND_OF_MINUTE));
    }

    @Test
    public void shouldReturnCurrentTimestamp() {

        final Instant now = Instant.now(clock);

        assertEquals(clock.instant().getEpochSecond(), now.getEpochSecond());
    }
}
