package com.baeldung.setenvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class SettingDockerEnvironmentVariableUnitTest {

    public static final String ENV_VARIABLE_NAME = "CUSTOM_DOCKER_ENV_VARIABLE";
    public static final String ENV_VARIABLE_VALUE = "TRUE";

    @Test
    @EnabledIfEnvironmentVariable(named = ENV_VARIABLE_NAME, matches = ENV_VARIABLE_VALUE)
    void givenDockerEnvironment_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
        String actual = System.getenv(ENV_VARIABLE_NAME);
        assertEquals(ENV_VARIABLE_VALUE, actual);
    }
}
