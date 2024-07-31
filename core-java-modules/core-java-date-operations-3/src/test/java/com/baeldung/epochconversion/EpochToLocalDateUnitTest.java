package com.baeldung.epochconversion;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpochToLocalDateUnitTest {

    @Test
    void givenEpoch_thenComputeLocalDateCorrectly() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate expectedDate = LocalDate.of(1995, 4, 15);
        long date = expectedDate.atStartOfDay(zoneId).toInstant().toEpochMilli();

        LocalDate actualDate =
            Instant.ofEpochMilli(date)
                .atZone(zoneId)
                .toLocalDate();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void givenEpoch_thenComputeLocalDateTimeCorrectly() {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime expectedTime = LocalDateTime.parse("2019-11-15T13:15:30");
        long time = expectedTime.atZone(zoneId).toInstant().toEpochMilli();

        LocalDateTime actualTime =
            Instant.ofEpochMilli(time)
                .atZone(zoneId)
                .toLocalDateTime();
        assertEquals(expectedTime, actualTime);
    }
}