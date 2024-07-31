package com.baeldung.environmentvariablesfortest;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.EnvironmentVariablesRule;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentVariablesSystemStubsJUnit4UnitTest {
    @Rule
    public EnvironmentVariablesRule environmentVariablesRule =
      new EnvironmentVariablesRule("system stubs", "initializes variable");

    @Test
    public void givenEnvironmentSetUpInObject_thenCanReadIt() {
        assertThat(System.getenv("system stubs")).isEqualTo("initializes variable");
    }
}
