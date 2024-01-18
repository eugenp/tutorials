package com.baeldung.setenvironment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

class SettingEnvironmentVariableUnitTest {

    @Test
    @SetEnvironmentVariable(key = "test", value = "Hello World")
    void givenVariableSet_whenGetenv_thenGetCorrectValue() {
        String expected = "Hello World";
        String actual = System.getenv("test");
        assertThat(actual).isEqualTo(expected);
    }
}
