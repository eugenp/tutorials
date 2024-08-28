package com.baeldung.immutable.auxiliary;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePersonAuxiliaryUnitTest {

    @Test
    public void whenComparing_shouldIgnore() throws Exception {
        final ImmutablePerson john1 = ImmutablePerson.builder()
          .name("John")
          .age(42)
          .auxiliaryField("Value1")
          .build();

        final ImmutablePerson john2 = ImmutablePerson.builder()
          .name("John")
          .age(42)
          .auxiliaryField("Value2")
          .build();


        assertThat(john1.equals(john2))
          .isTrue();

        assertThat(john1.toString())
          .isEqualTo(john2.toString());

        assertThat(john1.hashCode())
          .isEqualTo(john2.hashCode());
    }
}