package com.baeldung.immutable.default_;

import org.junit.Test;

public class ImmutablePersonDefaultUnitTest {

    @Test
    public void whenInstantiating_shouldUseDefaultValue() throws Exception {

        final ImmutablePerson john = ImmutablePerson.builder().name("John").build();

        assertThat(john.getAge()).isEqualTo(42);

    }
}