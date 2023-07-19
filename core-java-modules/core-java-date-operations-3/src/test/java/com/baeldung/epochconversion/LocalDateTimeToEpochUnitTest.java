package com.baeldung.epochconversion;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateTimeToEpochUnitTest {

    @Test
    void givenDate_thenComputeEpochCorrectly() {
        ZoneId zoneId = ZoneId.of("Europe/Tallinn");
        long expectedEpoch = LocalDate.now().toEpochDay();
        LocalDateTime givenDate = Instant.ofEpochMilli(expectedEpoch)
            .atZone(zoneId)
            .toLocalDateTime();

        long actualEpoch = givenDate.atZone(zoneId).toInstant().toEpochMilli();
        assertEquals(expectedEpoch, actualEpoch);
    }

    @Test
    void givenTime_thenComputeEpochCorrectly() {
        ZoneId zoneId = ZoneId.of("Europe/Amsterdam");
        long expectedEpoch = Instant.now().toEpochMilli();
        LocalDateTime givenTime = Instant.ofEpochMilli(expectedEpoch)
            .atZone(zoneId)
            .toLocalDateTime();

        long actualEpoch = givenTime.atZone(zoneId).toInstant().toEpochMilli();
        assertEquals(expectedEpoch, actualEpoch);
    }
}