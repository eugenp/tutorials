package com.baeldung.enabledif;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(Spring5EnabledAnnotationIntegrationTest.Config.class)
@TestPropertySource(properties = { "tests.enabled=true" })
public class Spring5EnabledAnnotationIntegrationTest {
    
    @Configuration
    static class Config {
    }

    @EnabledIf("true")
    @Test
    void givenEnabledIfLiteral_WhenTrue_ThenTestExecuted() {
        assertTrue(true);
    }

    @EnabledIf(expression = "${tests.enabled}", loadContext = true)
    @Test
    void givenEnabledIfExpression_WhenTrue_ThenTestExecuted() {
        assertTrue(true);
    }

    @EnabledIf("#{systemProperties['java.version'].startsWith('1.8')}")
    @Test
    void givenEnabledIfSpel_WhenTrue_ThenTestExecuted() {
        assertTrue(true);
    }

    @EnabledOnJava8
    @Test
    void givenEnabledOnJava8_WhenTrue_ThenTestExecuted() {
        assertTrue(true);
    }

    @DisabledIf("#{systemProperties['java.version'].startsWith('1.7')}")
    @Test
    void givenDisabledIf_WhenTrue_ThenTestNotExecuted() {
        assertTrue(true);
    }

}
