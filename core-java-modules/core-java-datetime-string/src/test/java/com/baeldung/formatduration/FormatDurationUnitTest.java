package com.baeldung.formatduration;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class FormatDurationUnitTest {

    private FormatDuration formatDuration = new FormatDuration();

    @Test
    public void givenSeconds_WhenUsingFormatter_FormatDuration() {
        long s = 36000000;
        assertThat(String.format("%2d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60))).isEqualTo("10000:00:00");
    }

    @Test
    public void givenInterval_WhenFormatInterval_formatDuration() {
        assertThat(formatDuration.formatDurationUsingTimeUnit(36000000)).isEqualTo("10:00:00");
    }

    @Test
    public void givenInterval_WhenFormatUsingDuration_formatDuration() {
        assertThat(formatDuration.formatUsingDuration(Duration.ofMillis(36000000))).isEqualTo("10:00:00");
    }


    @Test
    public void givenInterval_WhenFormatDurationUsingApacheCommons_formatDuration() {
        assertThat(DurationFormatUtils.formatDuration(36000000, "HH:MM:SS", true))
          .isEqualTo("10:00:00");
    }

    @Test
    public void givenInterval_WhenFormatDurationUsingJodaTime_formatDuration() {
        assertThat(formatDuration.formatDurationUsingJodaTime((36000000))).isEqualTo("10:00:00");
    }
}
