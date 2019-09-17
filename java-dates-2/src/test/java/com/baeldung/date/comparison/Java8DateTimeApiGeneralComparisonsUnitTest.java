package com.baeldung.date.comparison;

import org.junit.Test;

import java.time.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Java8DateTimeApiGeneralComparisonsUnitTest {

    @Test
    public void givenLocalDates_whenComparing_thenAssertsPass() {
        LocalDate firstDate = LocalDate.of(2019, 8, 10);
        LocalDate secondDate = LocalDate.of(2019, 7, 1);
        LocalDate thirdDate = LocalDate.of(2019, 7, 1); // same date as secondDate

        assertThat(firstDate.isAfter(secondDate), is(true));
        assertThat(firstDate.isBefore(secondDate), is(false));

        assertThat(firstDate.isEqual(secondDate), is(false));
        assertThat(firstDate.equals(secondDate), is(false));

        assertThat(firstDate.compareTo(secondDate), is(1));
        assertThat(secondDate.compareTo(firstDate), is(-1));

        assertThat(secondDate.isAfter(thirdDate), is(false));
        assertThat(secondDate.isBefore(thirdDate), is(false));
        assertThat(secondDate.isEqual(thirdDate), is(true));
        assertThat(secondDate.equals(thirdDate), is(true));
        assertThat(secondDate.compareTo(thirdDate), is(0));
    }

    @Test
    public void givenLocalDateTimes_whenComparing_thenAssertsPass() {
        LocalDateTime firstTimestamp = LocalDateTime.of(2019, 8, 10, 11, 30, 0);
        LocalDateTime secondTimestamp = LocalDateTime.of(2019, 8, 10, 11, 15, 0);
        LocalDateTime thirdTimestamp = LocalDateTime.of(2019, 8, 10, 11, 15, 0); // same as secondTimestamp

        assertThat(firstTimestamp.isAfter(secondTimestamp), is(true));
        assertThat(firstTimestamp.isBefore(secondTimestamp), is(false));

        assertThat(firstTimestamp.isEqual(secondTimestamp), is(false));
        assertThat(firstTimestamp.equals(secondTimestamp), is(false));

        assertThat(firstTimestamp.compareTo(secondTimestamp), is(1));
        assertThat(secondTimestamp.compareTo(firstTimestamp), is(-1));

        assertThat(secondTimestamp.isAfter(thirdTimestamp), is(false));
        assertThat(secondTimestamp.isBefore(thirdTimestamp), is(false));
        assertThat(secondTimestamp.isEqual(thirdTimestamp), is(true));
        assertThat(secondTimestamp.compareTo(thirdTimestamp), is(0));
    }

    @Test
    public void givenZonedDateTimes_whenComparing_thenAssertsPass() {
        ZonedDateTime timeInNewYork = ZonedDateTime.of(2019, 8, 10, 8, 0, 0, 0,
          ZoneId.of("America/New_York"));
        ZonedDateTime timeInBerlin = ZonedDateTime.of(2019, 8, 10, 14, 0, 0, 0,
          ZoneId.of("Europe/Berlin"));

        assertThat(timeInNewYork.isAfter(timeInBerlin), is(false));
        assertThat(timeInNewYork.isBefore(timeInBerlin), is(false));

        assertThat(timeInNewYork.isEqual(timeInBerlin), is(true));
        assertThat(timeInNewYork.equals(timeInBerlin), is(false));

        assertThat(timeInNewYork.compareTo(timeInBerlin), is(-1));
    }

    @Test
    public void givenLocalTimes_whenComparing_thenAssertsPass() {
        LocalTime firstTime = LocalTime.of(8, 30);
        LocalTime secondTime = LocalTime.of(9, 45);

        assertThat(firstTime.isAfter(secondTime), is(false));
        assertThat(firstTime.isBefore(secondTime), is(true));

        assertThat(firstTime.equals(secondTime), is(false));

        assertThat(firstTime.compareTo(secondTime), is(-1));
    }
}