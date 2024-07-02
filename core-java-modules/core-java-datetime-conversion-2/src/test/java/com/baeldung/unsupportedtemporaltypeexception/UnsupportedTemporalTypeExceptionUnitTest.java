package com.baeldung.unsupportedtemporaltypeexception;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

class UnsupportedTemporalTypeExceptionUnitTest {

    @Test
    void givenLocalDateTime_whenConvertingToInstant_thenThrowException() {
        assertThatThrownBy(() -> {
            LocalDateTime localDateTime = LocalDateTime.now();
            long seconds = localDateTime.getLong(ChronoField.INSTANT_SECONDS);
            Instant instant = Instant.ofEpochSecond(seconds);
        }).isInstanceOf(UnsupportedTemporalTypeException.class)
            .hasMessage("Unsupported field: InstantSeconds");
    }

    @Test
    void givenLocalDateTime_whenConvertingUsingTimeZone_thenDoNotThrowException() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        assertThatCode(() -> {
            Instant instant = zonedDateTime.toInstant();
        }).doesNotThrowAnyException();
    }

}
