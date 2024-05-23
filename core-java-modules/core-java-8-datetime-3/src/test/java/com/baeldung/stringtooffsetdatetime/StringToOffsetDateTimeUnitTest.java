package com.baeldung.stringtooffsetdatetime;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class StringToOffsetDateTimeUnitTest {
    String dateTimeString = "2024-04-11T10:15:30+01:00";

    @Test
    public void givenDateTimeString_whenUsingOffsetDateTimeParse_thenConvertToOffsetDateTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString);

        OffsetDateTime expected = OffsetDateTime.of(2024, 4, 11, 10, 15, 30, 0, ZoneOffset.ofHours(1));
        assertEquals(expected, offsetDateTime);
    }

    @Test
    public void givenDateTimeStringAndFormatter_whenUsingDateTimeFormatter_thenConvertToOffsetDateTime() {
        String customDateTimeString = "11-04-2024 10:15:30 +0100";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z");

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(customDateTimeString, formatter);

        OffsetDateTime expected = OffsetDateTime.of(2024, 4, 11, 10, 15, 30, 0, ZoneOffset.ofHours(1));
        assertEquals(expected, offsetDateTime);
    }
}
