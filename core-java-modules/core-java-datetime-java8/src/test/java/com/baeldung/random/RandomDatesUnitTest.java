package com.baeldung.random;

import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class RandomDatesUnitTest {

    @RepeatedTest(100)
    void givenNoRange_WhenGenDate_ShouldGenerateRandomDates() {
        LocalDate randomDay = RandomDates.date();

        assertThat(randomDay).isAfter(LocalDate.MIN).isBefore(LocalDate.MAX);
    }

    @RepeatedTest(100)
    void givenARange_WhenGenDate_ShouldBeInRange() {
        LocalDate start = LocalDate.of(1989, Month.OCTOBER, 14);
        LocalDate end = LocalDate.now();

        LocalDate random = RandomDates.between(start, end);
        assertThat(random).isAfter(start).isBefore(end);
    }
}
