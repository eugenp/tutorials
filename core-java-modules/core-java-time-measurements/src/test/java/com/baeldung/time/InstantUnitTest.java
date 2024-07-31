package com.baeldung.time;

import org.junit.Test;
import org.mockito.MockedStatic;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

public class InstantUnitTest {

    @Test
    public void givenInstantMock_whenNow_thenGetFixedInstant() {
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);
            Instant now = Instant.now();
            assertThat(now.toString()).isEqualTo(instantExpected);
        }
    }

    @Test
    public void givenFixedClock_whenNow_thenGetFixedInstant() {
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));

        Instant instant = Instant.now(clock);

        assertThat(instant.toString()).isEqualTo(instantExpected);
    }
}
