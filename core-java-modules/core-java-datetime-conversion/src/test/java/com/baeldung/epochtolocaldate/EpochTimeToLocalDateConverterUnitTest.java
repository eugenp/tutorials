package com.baeldung.epochtolocaldate;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpochTimeToLocalDateConverterUnitTest {
    @Test
    public void testConvertEpochTimeToLocalDate() {
        long epochTimeMillis = 1624962431000L; // Example epoch time in millisecond
        LocalDate expectedDate = LocalDate.of(2021, 6, 29);

        Instant instant = Instant.ofEpochMilli(epochTimeMillis);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate actualDate = instant.atZone(zoneId).toLocalDate();

        assertEquals(expectedDate, actualDate);
    }
}
