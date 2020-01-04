package com.baeldung.date.comparison;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class DateTimeComparisonUtilsUnitTest {

    @Test
    void givenLocalDateTimes_whenIsSameDay_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 11, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusHours(5);
        LocalDateTime thirdTimestamp = firstTimestamp.plusDays(1);

        assertThat(DateTimeComparisonUtils.isSameDay(firstTimestamp, secondTimestamp), is(true));

        assertThat(DateTimeComparisonUtils.isSameDay(secondTimestamp, thirdTimestamp), is(false));
    }

    @Test
    void givenLocalDateAndLocalDateTime_whenIsSameDay_thenCompareTrue() {
        LocalDate localDate = LocalDate.of(2019, 8, 10);
        LocalDateTime localDateTime = LocalDateTime.of(2019, 8, 10, 11, 30, 0);

        assertThat(DateTimeComparisonUtils.isSameDay(localDateTime, localDate), is(true));
    }

    @Test
    void givenLocalDateTimes_whenIsSameHour_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 00, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusMinutes(15);
        LocalDateTime thirdTimestamp = firstTimestamp.plusHours(2);

        assertThat(DateTimeComparisonUtils.isSameHour(firstTimestamp, secondTimestamp), is(true));

        assertThat(DateTimeComparisonUtils.isSameHour(secondTimestamp, thirdTimestamp), is(false));
    }

    @Test
    void givenLocalDateTimes_whenIsSameMinute_thenCompareTrue() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 8, 15, 0);
        LocalDateTime secondTimestamp = firstTimestamp.plusSeconds(30);
        LocalDateTime thirdTimestamp = firstTimestamp.plusMinutes(5);

        assertThat(DateTimeComparisonUtils.isSameMinute(firstTimestamp, secondTimestamp), is(true));

        assertThat(DateTimeComparisonUtils.isSameMinute(secondTimestamp, thirdTimestamp), is(false));
    }

    @Test
    void givenZonedDateTimes_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 0, 0, 30,
          ZoneId.of("America/New_York"));
        ZonedDateTime zonedTimestampToCompare = ZonedDateTime.of(2019, 8, 10, 14, 0, 0, 0,
          ZoneId.of("Europe/Berlin"));

        assertThat(DateTimeComparisonUtils.isSameHour(zonedTimestamp, zonedTimestampToCompare), is(true));
    }

    @Test
    void givenZonedDateTimeAndLocalDateTime_whenIsSameHour_thenCompareTrue() {
        ZonedDateTime zonedTimestamp = ZonedDateTime.of(2019, 8, 10, 8, 15, 0, 0,
          ZoneId.of("America/New_York"));
        LocalDateTime localTimestamp = LocalDateTime.of(2019, 8, 10, 14, 20, 0);
        ZoneId zoneId = ZoneId.of("Europe/Berlin");

        assertThat(DateTimeComparisonUtils.isSameHour(zonedTimestamp, localTimestamp, zoneId), is(true));
    }
}