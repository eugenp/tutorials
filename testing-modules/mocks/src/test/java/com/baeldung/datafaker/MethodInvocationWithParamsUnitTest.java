package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MethodInvocationWithParamsUnitTest {

    @Test
    void testGetDurationFromExpression() {
        assertThat(MethodInvocationWithParams.getDurationFromExpression()).isNotBlank();
    }

    @Test
    void testGetDurationFromMethod() {
        assertThat(MethodInvocationWithParams.getDurationFromMethod())
                .isNotNull()
                .isBetween(Duration.ofSeconds(MethodInvocationWithParams.MIN),
                        Duration.ofSeconds(MethodInvocationWithParams.MAX));
    }
}