package com.baeldung.randomgenerators;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SplittableGeneratorMultiThreadUnitTest {

    @Test
    void givenSplittableGenerator_whenUsingMultipleThreads_thenListOfIntsIsGenerated() {
        List<Integer> numbers = SplittableGeneratorMultiThread.generateNumbersInMultipleThreads();
        assertThat(numbers).hasSize(20).allMatch(number -> number >= 0 && number <= 10);
    }

}
