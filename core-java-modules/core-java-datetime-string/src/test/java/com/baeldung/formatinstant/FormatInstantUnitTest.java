package com.baeldung.formatinstant;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.TimeZone;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class FormatInstantUnitTest {
    
    private static final String PATTERN_FORMAT = "dd.MM.yyyy";

    @Test
    public void givenInstant_whenUsingDateTimeFormatter_thenFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(TimeZone.getTimeZone("UTC").toZoneId());

        Instant instant = Instant.parse("2022-02-15T18:35:24.00Z");
        String formattedInstant = formatter.format(instant);

        assertThat(formattedInstant).isEqualTo("15.02.2022");
    }

    @Test(expected = UnsupportedTemporalTypeException.class)
    public void givenInstant_whenNotSpecifyingTimeZone_thenThrowException() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT);

        Instant instant = Instant.now();
        formatter.format(instant);
    }

    @Test
    public void givenInstant_whenUsingToString_thenFormat() {
        Instant instant = Instant.ofEpochMilli(1641828224000L);
        String formattedInstant = instant.toString();

        assertThat(formattedInstant).isEqualTo("2022-01-10T15:23:44Z");
    }
    
    @Test
    public void givenInstant_whenUsingJodaTime_thenFormat() {
        org.joda.time.Instant instant = new org.joda.time.Instant("2022-03-20T10:11:12");
        
        String formattedInstant = DateTimeFormat.forPattern(PATTERN_FORMAT)
            .print(instant);

        assertThat(formattedInstant).isEqualTo("20.03.2022");
    }

}
