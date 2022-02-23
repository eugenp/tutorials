package com.baeldung.time;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeUnitTest {

    @Test
    public void givenFixedClock_whenNow_thenGetFixedLocalDateTime() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        String dateTimeExpected = "2014-12-22T10:15:30";

        LocalDateTime dateTime = LocalDateTime.now(clock);

        assertThat(dateTime).isEqualTo(dateTimeExpected);
    }
}
