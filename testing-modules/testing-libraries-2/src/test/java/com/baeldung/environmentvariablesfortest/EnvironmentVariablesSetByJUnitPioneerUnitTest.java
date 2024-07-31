package com.baeldung.environmentvariablesfortest;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;

@SetEnvironmentVariable(key = "pioneer", value = "is pioneering")
class EnvironmentVariablesSetByJUnitPioneerUnitTest {

    @Test
    void givenEnvironmentVariableIsSetForClass_thenVariableCanBeRead() {
        assertThat(System.getenv("pioneer")).isEqualTo("is pioneering");
    }

    @ClearEnvironmentVariable(key = "pioneer")
    @Test
    void givenEnvironmentVariableIsClear_thenItIsNotSet() {
        assertThat(System.getenv("pioneer")).isNull();
    }
}
