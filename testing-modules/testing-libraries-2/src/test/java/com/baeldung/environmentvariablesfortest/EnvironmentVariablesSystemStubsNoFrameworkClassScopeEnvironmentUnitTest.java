package com.baeldung.environmentvariablesfortest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

class EnvironmentVariablesSystemStubsNoFrameworkClassScopeEnvironmentUnitTest {
    private static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @BeforeAll
    static void beforeAll() throws Exception {
        environmentVariables.set("system stubs", "in test");
        environmentVariables.setup();
    }

    @AfterAll
    static void afterAll() throws Exception {
        environmentVariables.teardown();
    }

    @Test
    void givenSetEnvironmentVariablesBeforeAll_thenCanBeRead() throws Exception {
        assertThat(System.getenv("system stubs")).isEqualTo("in test");
    }
}
