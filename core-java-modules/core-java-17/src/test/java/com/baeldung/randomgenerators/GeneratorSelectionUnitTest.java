package com.baeldung.randomgenerators;

import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;

class GeneratorSelectionUnitTest {

    @Test
    void givenDefaultGenerator_whenGeneratingAnInt_thenIntInRangeIsGenerated() {
        RandomGenerator generator = RandomGenerator.getDefault();
        int number = generator.nextInt(10);
        assertThat(number).isNotNegative().isLessThan(10);
    }

    @Test
    void givenSpecificGenerator_whenGeneratingAnInt_thenIntInRangeIsGenerated() {
        RandomGenerator generator = RandomGenerator.of("L128X256MixRandom");
        int number = generator.nextInt(10);
        assertThat(number).isNotNegative().isLessThan(10);
    }

}
