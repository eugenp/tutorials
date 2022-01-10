package com.baeldung.randomgenerators;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OldRandomUnitTest {

    @Test
    void givenOldRandomApi_whenGeneratingAnInt_thenIntInRangeIsGenerated() {
        int number = OldRandom.getRandomInt(10);
        assertThat(number).isNotNegative().isLessThan(10);
    }

}
