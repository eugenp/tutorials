package com.baeldung.immutable;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePersonTest {

    @Test
    public void whenModifying_shouldCreateNewInstance() throws Exception {
        final com.baeldung.immutable.ImmutablePerson john = com.baeldung.immutable.ImmutablePerson.builder()
          .age(42)
          .name("John")
          .build();

        final com.baeldung.immutable.ImmutablePerson john43 = john.withAge(43);

        assertThat(john)
          .isNotSameAs(john43);
        assertThat(john.getAge())
          .isEqualTo(42);
    }
}