package com.baeldung.unixtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class UnixTimeUtilsUnitTest {
    private static final String AUG_16_2022_15h25m32_Z_FORMATTED = "2022-08-16T15:25:32";
    private static final long AUG_16_2022_15h25m32_Z_NANOS = 1660663532747420283l;
    private static final long AUGUST = 8;

    private void assertInstantFieldsMatch(LocalDateTime time) {
        assertEquals(time.get(ChronoField.MONTH_OF_YEAR), AUGUST);
        assertEquals(time.get(ChronoField.DAY_OF_MONTH), 16);
        assertEquals(time.get(ChronoField.YEAR), 2022);
        assertEquals(time.get(ChronoField.HOUR_OF_DAY), 15);
        assertEquals(time.get(ChronoField.MINUTE_OF_HOUR), 25);
        assertEquals(time.get(ChronoField.SECOND_OF_MINUTE), 32);
    }

    @Test
    void givenMillis_whenDateFrom_thenLocalTimeMatches() {
        long millis = AUG_16_2022_15h25m32_Z_NANOS / 1000 / 1000;

        Date date = UnixTimeUtils.dateFrom(millis);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(date.toInstant());
        assertInstantFieldsMatch(time);
    }

    @Test
    void givenMillis_whenCalendarFrom_thenLocalTimeMatches() {
        long millis = AUG_16_2022_15h25m32_Z_NANOS / 1000 / 1000;

        Calendar calendar = UnixTimeUtils.calendarFrom(millis);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(calendar.toInstant());
        assertInstantFieldsMatch(time);
    }

    @Test
    void whenInstantFromNanos_thenLocalTimeMatches() {
        Instant instant = UnixTimeUtils.fromNanos(AUG_16_2022_15h25m32_Z_NANOS);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertThat(time.toString()
            .startsWith(AUG_16_2022_15h25m32_Z_FORMATTED)).isTrue();
    }

    @Test
    void givenWrongPrecision_whenInstantFromNanos_thenUnexpectedTime() {
        long microseconds = AUG_16_2022_15h25m32_Z_NANOS / 1000;

        Instant instant = UnixTimeUtils.fromNanos(microseconds);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertThat(!time.toString()
            .startsWith(AUG_16_2022_15h25m32_Z_FORMATTED ));
        assertEquals("1970-01-20T05:17:43.532747420", time.toString());
    }

    @Test
    void givenNanos_whenInstantFromTimestamp_thenLocalTimeMatches() {
        Instant instant = UnixTimeUtils.fromTimestamp(AUG_16_2022_15h25m32_Z_NANOS);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertInstantFieldsMatch(time);
    }

    @Test
    void givenMicroseconds_whenInstantFromTimestamp_thenLocalTimeMatches() {
        long microseconds = AUG_16_2022_15h25m32_Z_NANOS / 1000;

        Instant instant = UnixTimeUtils.fromTimestamp(microseconds);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertInstantFieldsMatch(time);
    }

    @Test
    void givenMillis_whenInstantFromTimestamp_thenLocalTimeMatches() {
        long millis = AUG_16_2022_15h25m32_Z_NANOS / 1000 / 1000;

        Instant instant = UnixTimeUtils.fromTimestamp(millis);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertInstantFieldsMatch(time);
    }

    @Test
    void givenSeconds_whenInstantFromTimestamp_thenLocalTimeMatches() {
        long seconds = AUG_16_2022_15h25m32_Z_NANOS / 1000 / 1000 / 1000;

        Instant instant = UnixTimeUtils.fromTimestamp(seconds);

        LocalDateTime time = UnixTimeUtils.localTimeUtc(instant);
        assertInstantFieldsMatch(time);
    }
}
