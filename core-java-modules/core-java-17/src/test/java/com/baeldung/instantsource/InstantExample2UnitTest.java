package com.baeldung.instantsource;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class InstantExample2UnitTest {

    @Test
    void getCurrentInstant_shouldReturnCurrentInstantBasedOnSystemClock() {
        //given
        InstantSource instantSource = InstantSource.system();
        InstantExample2 tested = new InstantExample2(instantSource);
        Instant currentInstant = instantSource.instant();

        //when
        Instant returnedInstant = tested.getCurrentInstant();

        //then
        assertTrue(returnedInstant.isAfter(currentInstant));
    }

    @Test
    void getCurrentInstant_shouldReturnFixedInstant() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantSource instantSource = InstantSource.fixed(now.toInstant(ZoneOffset.UTC));
        InstantExample2 tested = new InstantExample2(instantSource);
        Instant currentInstant = instantSource.instant();

        LocalDateTime fixed = LocalDateTime.of(2022, 01, 01, 00, 00);
        Instant i = InstantSource.fixed(fixed.toInstant(ZoneOffset.UTC)).instant();
        System.out.println(i);

        //when
        Instant returnedInstant = tested.getCurrentInstant();

        //then
        assertEquals(currentInstant, returnedInstant);
    }

    @Test
    void getCurrentInstant_shouldMatchGivenTimezone() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantSource instantSource = InstantSource.fixed(now.toInstant(ZoneOffset.of("-4")));
        InstantExample2 tested = new InstantExample2(instantSource);

        //when
        Instant returnedInstant = tested.getCurrentInstant();

        //then
        assertEquals(now.atOffset(ZoneOffset.of("-4")).toInstant(), returnedInstant);
    }
}