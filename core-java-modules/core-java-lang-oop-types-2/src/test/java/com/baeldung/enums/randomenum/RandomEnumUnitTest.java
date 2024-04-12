package com.baeldung.enums.randomenum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomEnumUnitTest {

    @Test
    public void givenEnumType_whenUsingStaticMethod_valueIsRandomlyGenerated() {
        Direction direction = Direction.randomDirection();
        assertThat(direction).isNotNull();
        assertThat(direction instanceof Direction);
    }

    @Test
    public void givenEnumType_whenGeneratingRandomValue_valueIsOfClassAndNotNull() {
        RandomEnumGenerator reg = new RandomEnumGenerator(Direction.class);
        Object direction = reg.randomEnum();
        assertThat(direction).isNotNull();
        assertThat(direction instanceof Direction);
    }
}
