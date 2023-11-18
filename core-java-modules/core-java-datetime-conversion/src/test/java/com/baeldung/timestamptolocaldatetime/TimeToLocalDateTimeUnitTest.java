package com.baeldung.timestamptolocaldatetime;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TimeToLocalDateTimeUnitTest {
    private static final long timestampInMillis = 1700010123000L;
    private static final String expectedTimestampString = "2023-11-15 01:02:03";

    @Test
    public void givenTimestamp_whenConvertingToLocalDateTime_thenConvertSuccessfully() {
        Instant instant = Instant.ofEpochMilli(timestampInMillis);
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        assertEquals(expectedTimestampString, formattedDateTime);
    }
}
