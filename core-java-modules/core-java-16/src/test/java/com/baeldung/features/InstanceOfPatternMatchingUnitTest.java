package com.baeldung.features;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InstanceOfPatternMatchingUnitTest {

    @Test
    void givenTheNewPatternMatchingAbility_whenComparingAgainstTheTradiationalApproach_thenBothVariablesAreEqual() {
        Object obj = "TEST";

        if (obj instanceof String a) {
            String b = (String) obj;
            assertThat(a).isEqualTo(b);
        }
    }
}
