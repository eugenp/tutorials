package com.baeldung.random;

import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class RandomTimesUnitTest {

    @RepeatedTest(100)
    void givenARange_WhenGenTime_ShouldBeInRange() {
        LocalTime morning = LocalTime.of(8, 30);
        LocalTime randomTime = RandomTimes.between(LocalTime.MIDNIGHT, morning);

        assertThat(randomTime)
                .isAfter(LocalTime.MIDNIGHT).isBefore(morning)
                .isAfter(LocalTime.MIN).isBefore(LocalTime.MAX);
    }
}
