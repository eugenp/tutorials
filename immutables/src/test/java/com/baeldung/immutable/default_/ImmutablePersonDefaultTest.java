package com.baeldung.immutable.default_;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutablePersonDefaultTest {

    @Test
    public void whenInstantiating_shouldUseDefaultValue() throws Exception {

        final com.baeldung.immutable.default_.ImmutablePerson john = com.baeldung.immutable.default_.ImmutablePerson.builder().name("John").build();

        assertThat(john.getAge()).isEqualTo(42);

    }
}