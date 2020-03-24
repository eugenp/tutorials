package com.baeldung.immutable;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

public class ImmutablePersonUnitTest {

    @Test
    public void whenModifying_shouldCreateNewInstance() throws Exception {
        final ImmutablePerson john = ImmutablePerson.builder()
          .age(42)
          .name("John")
          .build();

        final ImmutablePerson john43 = john.withAge(43);

        assertThat(john)
          .isNotSameAs(john43);

        assertThat(john.getAge())
          .isEqualTo(42);

        assertImmutable(ImmutablePerson.class);
    }
}