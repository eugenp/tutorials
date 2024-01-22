package com.baeldung.setenvironment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class SettingDockerEnvironmentVariableUnitTest {

    public static final String ENV_VARIABLE_NAME = "TRUE";
    public static final String ENV_VARIABLE_VALUE = "CUSTOM_DOCKER_ENV_VARIABLE";

    @Test
    @EnabledIfEnvironmentVariable(named = ENV_VARIABLE_NAME, matches = ENV_VARIABLE_VALUE)
    void givenDockerEnvironment_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
        String actual = System.getenv(ENV_VARIABLE_NAME);
        assertThat(actual).isEqualTo(ENV_VARIABLE_VALUE);
    }
}
