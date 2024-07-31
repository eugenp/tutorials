package com.baeldung.staticmethods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StaticCounterUnitTest {

    @Test
    void givenStaticCounter_whenIncrementCounterIsCalled_thenValueIsIncresedByOne() {
        int oldValue = StaticCounter.getCounterValue();
        int newValue = StaticCounter.incrementCounter();
        assertThat(newValue).isEqualTo(oldValue + 1);
    }

}
