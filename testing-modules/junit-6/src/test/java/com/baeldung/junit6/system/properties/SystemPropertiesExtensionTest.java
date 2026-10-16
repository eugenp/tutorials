package com.baeldung.junit6.system.properties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.util.ClearSystemProperty;
import org.junit.jupiter.api.util.RestoreSystemProperties;
import org.junit.jupiter.api.util.SetSystemProperty;

class SystemPropertiesExtensionTest {

    @Test
    void whenSetProperty_thenLeaksToNextTest() {
        System.setProperty("app.mode", "debug");
        assertThat(System.getProperty("app.mode"))
            .isEqualTo("debug");
    }

    @Test
    @Disabled("only for demonstration purposes, fails if whenSetProperty_thenLeaksToNextTest runs first")
    void whenPropertyNotSet_thenShouldBeNull() {
        // fails if whenSetProperty_thenLeaksToNextTest runs first
        //   "app.mode" will is still be "debug"
        assertThat(System.getProperty("app.mode"))
            .isNull();
    }

    @Test
    @SetSystemProperty(key = "app.mode", value = "debug")
    void givenSetProperty_thenGetPropertyReturnsValue() {
        assertThat(System.getProperty("app.mode"))
            .isEqualTo("debug");
    }

    @Test
    @ClearSystemProperty(key = "app.mode")
    void whenNoProperty_thenGetPropertyShouldReturnNull() {
        assertThat(System.getProperty("app.mode"))
            .isNull();
    }

    @Test
    @ClearSystemProperty(key = "app.host")
    @ClearSystemProperty(key = "app.port")
    @SetSystemProperty(key = "app.mode", value = "staging")
    void givenRepeatableAnnotations_whenGetProperties_thenExpectedValues() {
        assertThat(System.getProperty("app.host"))
            .isNull();
        assertThat(System.getProperty("app.port"))
            .isNull();
        assertThat(System.getProperty("app.mode"))
            .isEqualTo("staging");
    }

    @Test
    @RestoreSystemProperties
    void whenCallingTestedFunction_thenReturnsNotEmptyString() {
        String result = testedFunction();
        assertThat(result).isNotEmpty();
    }

    static String testedFunction() {
        String randomString = UUID.randomUUID().toString();
        System.setProperty(randomString, "dummy-value");
        return "done";
    }

}
