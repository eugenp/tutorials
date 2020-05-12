package com.baeldung.date.comparison;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class LegacyDateComparisonUtilsUnitTest {

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void givenDatesWithDifferentHours_whenIsSameDay_thenReturnsTrue() {
        Date firstDate = toDate(LocalDateTime.of(2019, 8, 10, 11, 00, 00));
        Date secondDate = toDate(LocalDateTime.of(2019, 8, 10, 12, 00, 00));
        Date thirdDate = toDate(LocalDateTime.of(2019, 8, 15, 12, 00, 00));

        assertThat(LegacyDateComparisonUtils.isSameDay(firstDate, secondDate), is(true));
        assertThat(LegacyDateComparisonUtils.isSameDay(secondDate, thirdDate), is(false));
    }

    @Test
    void givenDatesWithingSameHour_whenIsSameHour_thenReturnsTrue() {
        Date firstDate = toDate(LocalDateTime.of(2019, 8, 10, 11, 00, 00));
        Date secondDate = toDate(LocalDateTime.of(2019, 8, 10, 11, 15, 00));
        Date thirdDate = toDate(LocalDateTime.of(2019, 8, 10, 12, 00, 00));

        assertThat(LegacyDateComparisonUtils.isSameHour(firstDate, secondDate), is(true));
        assertThat(LegacyDateComparisonUtils.isSameHour(secondDate, thirdDate), is(false));
    }

    @Test
    void givenDates__whenComparing_thenAssertsPass() {
        Date firstDate = toDate(LocalDateTime.of(2019, 8, 10, 0, 00, 00));
        Date secondDate = toDate(LocalDateTime.of(2019, 8, 15, 0, 00, 00));
        Date thirdDate = toDate(LocalDateTime.of(2019, 8, 15, 0, 00, 00)); // same date as secondDate

        assertThat(firstDate.after(secondDate), is(false));
        assertThat(firstDate.before(secondDate), is(true));
        assertThat(firstDate.compareTo(secondDate), is(-1));
        assertThat(firstDate.equals(secondDate), is(false));

        assertThat(thirdDate.after(secondDate), is(false));
        assertThat(thirdDate.before(secondDate), is(false));
        assertThat(thirdDate.compareTo(secondDate), is(0));
        assertThat(thirdDate.equals(secondDate), is(true));
    }
}