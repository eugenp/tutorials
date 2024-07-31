package com.baeldung.instantvslocaldatetime;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InstantAndLocalDateTimeUnitTest {

    @Test
    public void givenTwoInstants_whenComparing_thenDifferent() {

        // Instant arithmetic
        Instant.now().plus(12, ChronoUnit.HOURS);
        Instant oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);

        // Comparing 2 Instants
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(1, ChronoUnit.SECONDS);
        assertTrue(instant1.isBefore(instant2));
        assertFalse(instant1.isAfter(instant2));
    }

    @Test
    public void givenTwoLocalDateTimes_whenComparing_thenDifferent() {

        // LocalDateTime arithmetic
        LocalDateTime tomorrow = LocalDateTime.now().plus(1, ChronoUnit.DAYS);
        LocalDateTime oneYearAgo = LocalDateTime.now().minus(1, ChronoUnit.YEARS);

        // Comparing 2 LocalDateTimes
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime y2k = LocalDateTime.of(2000, 1, 1, 0, 0);
        assertTrue(now.isAfter(y2k));
        assertTrue(y2k.isBefore(now));
    }
}
