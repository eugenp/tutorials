package com.baeldung.epochtolocaldate;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpochTimeToLocalDateTimeConverterUnitTest {

    @Test
    public void testConvertEpochTimeToLocalDateTime() {
        long epochTimeMillis = 1624962431000L; // Example epoch time in milliseconds
        LocalDateTime expectedDateTime = LocalDateTime.of(2021, 6, 29, 10, 27, 11);

        Instant instant = Instant.ofEpochMilli(epochTimeMillis);
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDateTime actualDateTime = instant.atZone(zoneId).toLocalDateTime();

        assertEquals(expectedDateTime, actualDateTime);
    }

}
