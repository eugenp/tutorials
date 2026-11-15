package com.baeldung.junit6.system.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.util.ClearSystemProperty;
import org.junit.jupiter.api.util.SetSystemProperty;

@SetSystemProperty(key = "app.mode", value = "staging")
@ClearSystemProperty(key = "app.host")
class ClassLevelSystemPropertiesUnitTest {

    @Test
    void givenClassLevelAnnotations_whenGetProperties_thenExpectedValues() {
        assertThat(System.getProperty("app.mode"))
            .isEqualTo("staging");
        assertThat(System.getProperty("app.host"))
            .isNull();
    }

    @Test
    @SetSystemProperty(key = "app.mode", value = "debug")
    void givenMethodLevelAnnotation_whenGetProperty_thenOverridesClassLevel() {
        assertThat(System.getProperty("app.mode"))
            .isEqualTo("debug");
    }

}
