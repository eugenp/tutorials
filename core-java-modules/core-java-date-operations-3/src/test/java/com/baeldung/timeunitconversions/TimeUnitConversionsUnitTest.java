package com.baeldung.timeunitconversions;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TimeUnitConversionsUnitTest {


    @Test
    void givenSeconds_whenConvertToMinutes_thenCorrect() {
        int input = 60;

        long minutes = TimeUnit.MINUTES.convert(input, TimeUnit.SECONDS);

        assertThat(minutes).isEqualTo(1);
    }

    @Test
    void givenMinutes_whenConvertToSeconds_thenCorrect() {
        int input = 1;

        long seconds = TimeUnit.SECONDS.convert(input, TimeUnit.MINUTES);

        assertThat(seconds).isEqualTo(60);
    }

    @Test
    void givenSeconds_whenToMinutes_thenCorrect() {
        int input = 60;

        long minutes = TimeUnit.SECONDS.toMinutes(input);

        assertThat(minutes).isEqualTo(1);
    }

    @Test
    void givenMinutes_whenToSeconds_thenCorrect() {
        int input = 1;

        long seconds = TimeUnit.MINUTES.toSeconds(input);

        assertThat(seconds).isEqualTo(60);
    }

    @Test
    void givenNegativeInput_whenToMinutes_thenCorrect() {
        int input = -60;

        long minutes = TimeUnit.SECONDS.toMinutes(input);
        assertThat(minutes).isEqualTo(-1);
    }

    @Test
    void givenNonTotalInput_whenToMinutes_thenCorrectTotalResultWithDecimalTruncate() {
        long positiveUnder = TimeUnit.SECONDS.toMinutes(59);
        long positiveAbove = TimeUnit.SECONDS.toMinutes(61);

        assertThat(positiveUnder).isEqualTo(0);
        assertThat(positiveAbove).isEqualTo(1);
    }

    @Test
    void givenNonTotalNegativeInput_whenToMinutes_thenCorrectTotalResultWithDecimalTruncate() {
        long negativeUnder = TimeUnit.SECONDS.toMinutes(-59);
        long negativeAbove = TimeUnit.SECONDS.toMinutes(-61);

        assertThat(negativeUnder).isEqualTo(0);
        assertThat(negativeAbove).isEqualTo(-1);
    }

    @Test
    void givenOverflowInput_whenToMillis_thenTruncatedToLimit() {
        long maxMillis = TimeUnit.DAYS.toMillis(Long.MAX_VALUE);
        long minMillis = TimeUnit.DAYS.toMillis(Long.MIN_VALUE);

        assertThat(maxMillis).isEqualTo(Long.MAX_VALUE);
        assertThat(minMillis).isEqualTo(Long.MIN_VALUE);
    }

    @Test
    void givenInput_whenExtractFineTimeUnits_thenCorrect() {
        long inputSeconds = 3672;

        long hours = TimeUnit.SECONDS.toHours(inputSeconds);

        long secondsRemainingAfterHours = inputSeconds - TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(secondsRemainingAfterHours);

        long seconds = secondsRemainingAfterHours - TimeUnit.MINUTES.toSeconds(minutes);


        assertThat(hours).isEqualTo(1);
        assertThat(minutes).isEqualTo(1);
        assertThat(seconds).isEqualTo(12);

        assertThat(inputSeconds).isEqualTo(
            (hours * 60 * 60) +
            (minutes * 60) +
            (seconds)
        );
    }
}
