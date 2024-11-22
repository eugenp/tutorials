package com.baeldung.immutable;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertImmutable;

public class ImmutablePersonUnitTest {

    /**
     * commenting the test case, As after upgrading to java 11
     * assertImmutable is giving exception. Raised the issue to Mutability support team
     * https://github.com/MutabilityDetector/MutabilityDetector/issues/196
     */
    @Ignore
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