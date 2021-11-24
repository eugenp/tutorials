package com.baeldung.time;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocalDateTime.class })
public class LocalDateTimeUnitTest {

    @Test
    public void givenFixedClock_whenNow_thenGetFixedLocalDateTime() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));
        String dateTimeExpected = "2014-12-22T10:15:30";

        LocalDateTime dateTime = LocalDateTime.now(clock);

        assertThat(dateTime).isEqualTo(dateTimeExpected);
    }
}
