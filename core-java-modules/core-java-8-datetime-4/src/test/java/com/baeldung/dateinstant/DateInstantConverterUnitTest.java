package com.baeldung.dateinstant;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateInstantConverterUnitTest {

    @Test
    void shouldConvertDateToInstant() {
        Date date = new Date(1708752000000L);

        Instant instant = DateInstantConverter.toInstant(date);

        assertNotNull(instant);
        assertEquals(date.getTime(), instant.toEpochMilli());
    }

    @Test
    void shouldConvertInstantToDate() {
        Instant instant = Instant.ofEpochMilli(1708752000000L);

        Date date = DateInstantConverter.toDate(instant);

        assertNotNull(date);
        assertEquals(instant.toEpochMilli(), date.getTime());
    }

    @Test
    void shouldReturnNullWhenDateIsNull() {
        Instant instant = DateInstantConverter.toInstant(null);
        assertNull(instant);
    }

    @Test
    void shouldReturnNullWhenInstantIsNull() {
        Date date = DateInstantConverter.toDate(null);
        assertNull(date);
    }

    @Test
    void shouldPreserveMillisecondPrecisionInRoundTrip() {
        Instant originalInstant = Instant.now();

        Date date = DateInstantConverter.toDate(originalInstant);
        Instant convertedBack = DateInstantConverter.toInstant(date);

        assertEquals(originalInstant.toEpochMilli(),
                convertedBack.toEpochMilli());
    }

    @Test
    void shouldTruncateNanosecondsWhenConvertingToDate() {
        Instant instantWithNanos = Instant.ofEpochSecond(1000, 123456789);

        Date date = DateInstantConverter.toDate(instantWithNanos);
        Instant convertedBack = DateInstantConverter.toInstant(date);

        assertEquals(instantWithNanos.toEpochMilli(),
                convertedBack.toEpochMilli());
    }
}
