package com.baeldung.stringtooffsetdatetime;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class StringToOffsetDateTimeUnitTest {
    String dateTimeString = "2024-04-11T10:15:30+01:00";

    @Test
    public void givenDateTimeString_whenUsingOffsetDateTimeParse_thenConvertToOffsetDateTime() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString);

        OffsetDateTime expected = OffsetDateTime.of(2024, 4, 11, 10, 15, 30, 0, OffsetDateTime.parse(dateTimeString).getOffset());
        assertEquals(expected, offsetDateTime);
    }

    @Test
    public void givenDateTimeStringAndFormatter_whenUsingDateTimeFormatter_thenConvertToOffsetDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeString, formatter);

        OffsetDateTime expected = OffsetDateTime.of(2024, 4, 11, 10, 15, 30, 0, OffsetDateTime.parse(dateTimeString).getOffset());
        assertEquals(expected, offsetDateTime);
    }
}
