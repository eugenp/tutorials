package com.baeldung.instantsource;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstantExampleUnitTest {

    @Test
    void getCurrentInstantFromWrapper_shouldReturnCurrentInstantBasedOnSystemClock() {
        //given
        InstantExample tested = new InstantExample(InstantWrapper.of(), null);
        Instant currentInstant = Clock.systemDefaultZone().instant();
        //when
        Instant returnedInstant = tested.getCurrentInstantFromWrapper();

        //then
        assertTrue(returnedInstant.isAfter(currentInstant));
    }

    @Test
    void getCurrentInstantFromWrapper_shouldReturnFixedInstant() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantExample tested = new InstantExample(InstantWrapper.of(now), null);
        Instant currentInstant = now.toInstant(ZoneOffset.UTC);
        //when
        Instant returnedInstant = tested.getCurrentInstantFromWrapper();

        //then
        assertEquals(currentInstant, returnedInstant);
    }

    @Test
    void getCurrentInstantFromInstantSource_shouldReturnCurrentInstantBasedOnSystemClock() {
        //given
        InstantSource instantSource = InstantSource.system();
        InstantExample tested = new InstantExample(null, instantSource);
        Instant currentInstant = instantSource.instant();

        //when
        Instant returnedInstant = tested.getCurrentInstantFromInstantSource();

        //then
        assertTrue(returnedInstant.isAfter(currentInstant));
    }

    @Test
    void getCurrentInstantFromInstantSource_shouldReturnFixedInstant() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantSource instantSource = InstantSource.fixed(now.toInstant(ZoneOffset.UTC));
        InstantExample tested = new InstantExample(null, instantSource);
        Instant currentInstant = instantSource.instant();

        LocalDateTime fixed = LocalDateTime.of(2022, 01, 01, 00, 00);
        Instant i = InstantSource.fixed(fixed.toInstant(ZoneOffset.UTC)).instant();
        System.out.println(i);

        //when
        Instant returnedInstant = tested.getCurrentInstantFromInstantSource();

        //then
        assertEquals(currentInstant, returnedInstant);
    }

    @Test
    void getCurrentInstantFromInstantSource_shouldMatchGivenTimezone() {
        //given
        LocalDateTime now = LocalDateTime.now();
        InstantSource instantSource = InstantSource.fixed(now.toInstant(ZoneOffset.of("-4")));
        InstantExample tested = new InstantExample(null, instantSource);

        //when
        Instant returnedInstant = tested.getCurrentInstantFromInstantSource();

        //then
        assertEquals(now.atOffset(ZoneOffset.of("-4")).toInstant(), returnedInstant);
    }
}