package com.baeldung.randomgenerators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeneratorFactoryUnitTest {

    @Test
    void givenDefaultGenerator_whenGeneratingAnInt_thenIntInRangeIsGenerated() {
        int number = GeneratorFactory.getRandomInt(GeneratorFactory.getDefaultGenerator(), 10);
        assertThat(number).isNotNegative().isLessThan(10);
    }

    @Test
    void givenJumpableGenerator_whenGeneratingAnInt_thenIntInRangeIsGenerated() {
        int number = GeneratorFactory.getRandomInt(GeneratorFactory.getJumpableGenerator(), 10);
        assertThat(number).isNotNegative().isLessThan(10);
    }

}
