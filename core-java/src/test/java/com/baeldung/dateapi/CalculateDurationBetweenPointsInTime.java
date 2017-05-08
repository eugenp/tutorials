package com.baeldung.dateapi;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static junit.framework.TestCase.assertEquals;

public class CalculateDurationBetweenPointsInTime {

    @Test
    public void givenTwoPointsInTime_whenUsingDurationBetween_thenDurationInSecondsNanos() {

        //given
        LocalTime start = LocalTime.of(1, 20, 25, 1024);
        LocalTime end = LocalTime.of(5, 29, 28, 1544);

        //when
        Duration duration = Duration.between(start, end);

        //then
        assertEquals(14943L, duration.get(ChronoUnit.SECONDS));
        assertEquals(520L, duration.get(ChronoUnit.NANOS));

    }
}
