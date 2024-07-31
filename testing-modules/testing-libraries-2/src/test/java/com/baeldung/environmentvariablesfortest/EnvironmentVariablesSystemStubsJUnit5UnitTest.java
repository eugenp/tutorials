package com.baeldung.environmentvariablesfortest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SystemStubsExtension.class)
class EnvironmentVariablesSystemStubsJUnit5UnitTest {

    @SystemStub
    private EnvironmentVariables environmentVariables;

    @BeforeEach
    void beforeEach() {
        environmentVariables.set("systemstubs", "creates stub objects");
    }

    @Test
    void givenEnvironmentVariableHasBeenSet_thenCanReadIt() {
        assertThat(System.getenv("systemstubs")).isEqualTo("creates stub objects");
    }
}
