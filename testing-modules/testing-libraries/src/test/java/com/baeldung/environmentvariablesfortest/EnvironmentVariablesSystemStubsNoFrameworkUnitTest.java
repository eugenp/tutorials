package com.baeldung.environmentvariablesfortest;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.withEnvironmentVariables;

import org.junit.jupiter.api.Test;

class EnvironmentVariablesSystemStubsNoFrameworkUnitTest {

    @Test
    void givenSetEnvironmentVariablesInTest_thenCanBeRead() throws Exception {
        withEnvironmentVariables("system stubs", "in test")
          .execute(() -> {
              assertThat(System.getenv("system stubs")).isEqualTo("in test");
          });
    }
}
