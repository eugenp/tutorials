package com.baeldung.enums;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
