package com.baeldung.instantsource;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class InstantExample1UnitTest {

    @Test
    void getCurrentInstant_shouldReturnCurrentInstantBasedOnSystemClock() {
        //given
        InstantExample1 tested = new InstantExample1(InstantWrapper.of());
        Instant currentInstant = Clock.systemDefaultZone().instant();
        //when
        Instant returnedInstant = tested.getCurrentInstant();

        //then
        assertTrue(returnedInstant.isAfter(currentInstant));
    }

    @Test
    void getCurrentInstant_shouldReturnFixedInstant() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantExample1 tested = new InstantExample1(InstantWrapper.of(now));
        Instant currentInstant = now.toInstant(ZoneOffset.UTC);
        //when
        Instant returnedInstant = tested.getCurrentInstant();

        //then
        assertEquals(currentInstant, returnedInstant);
    }
}