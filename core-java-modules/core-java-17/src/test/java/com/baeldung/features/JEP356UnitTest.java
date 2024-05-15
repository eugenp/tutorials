package com.baeldung.features;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JEP356UnitTest {

    @Test
    void getPseudoInts_whenUsingAlgorithmXoroshiro128PlusPlus_shouldReturnStreamOfRandomInteger() {
        var algorithm = "Xoshiro256PlusPlus";
        var streamSize = 100;

        JEP356 jep356 = new JEP356();

        jep356.getPseudoInts(algorithm, streamSize)
            .forEach(value -> assertThat(value).isLessThanOrEqualTo(99));
    }
}
