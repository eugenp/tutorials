package com.baeldung.environmentvariablesfortest;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EnvironmentVariablesSystemLambdaUnitTest {

    @Test
    void givenSetEnvironmentVariablesInTest_thenCanBeRead() throws Exception {
        withEnvironmentVariable("system lambda", "in test")
          .execute(() -> {
              assertThat(System.getenv("system lambda")).isEqualTo("in test");
          });
    }
}
