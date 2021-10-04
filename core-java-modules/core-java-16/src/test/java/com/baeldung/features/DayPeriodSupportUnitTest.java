package com.baeldung.features;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class DayPeriodSupportUnitTest {

    @Test
    public void givenASpecificTime_whenFormattingUsingTheBSymbol_thenExpectVerbosePeriodOfDay() {
        LocalTime date = LocalTime.parse("15:25:08.690791");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");

        formatter = DateTimeFormatter.ofPattern("h BBBB");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");

        formatter = DateTimeFormatter.ofPattern("h BBBBB");
        assertThat(date.format(formatter)).isEqualTo("3 in the afternoon");
    }
}
