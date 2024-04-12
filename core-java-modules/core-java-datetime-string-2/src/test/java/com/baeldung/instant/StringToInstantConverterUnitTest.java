package com.baeldung.instant;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class StringToInstantConverterUnitTest {
    String stringDate = "09:15:30 PM, Sun 10/09/2022";
    String pattern = "hh:mm:ss a, EEE M/d/uuuu";

    @Test public void givenStringTimeStamp_whenConvertingWithInstantUsingTimeZone_thenConvertToInstant() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
        ZoneId zoneId = ZoneId.of("America/Chicago");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        assertThat(instant.toString()).isEqualTo("2022-10-10T02:15:30Z");
    }

    @Test public void givenStringTimeStamp_whenConvertingWithLocalDateTime_thenConvertToInstant() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(stringDate, dateTimeFormatter);
        assertThat(localDateTime.toString()).isEqualTo("2022-10-09T21:15:30");
    }
}