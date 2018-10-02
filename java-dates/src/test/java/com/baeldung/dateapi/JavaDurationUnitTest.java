package com.baeldung.dateapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class JavaDurationUnitTest {

    @Test
    public void test2() {
        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");

        Duration duration = Duration.between(start, end);

        assertFalse(duration.isNegative());

        assertEquals(60, duration.getSeconds());
        assertEquals(1, duration.toMinutes());

        Duration fromDays = Duration.ofDays(1);
        assertEquals(86400, fromDays.getSeconds());

        Duration fromMinutes = Duration.ofMinutes(60);
        assertEquals(1, fromMinutes.toHours());

        assertEquals(120, duration.plusSeconds(60).getSeconds());
        assertEquals(30, duration.minusSeconds(30).getSeconds());

        assertEquals(120, duration.plus(60, ChronoUnit.SECONDS).getSeconds());
        assertEquals(30, duration.minus(30, ChronoUnit.SECONDS).getSeconds());

        Duration fromChar1 = Duration.parse("P1DT1H10M10.5S");
        Duration fromChar2 = Duration.parse("PT10M");
    }

}
