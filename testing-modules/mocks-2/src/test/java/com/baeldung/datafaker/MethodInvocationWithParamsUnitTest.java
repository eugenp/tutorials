package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MethodInvocationWithParamsUnitTest {

    @Test
    void whenGettingDurationFromExpression_thenResultNotEmpty() {
        assertThat(MethodInvocationWithParams.getDurationFromExpression()).isNotBlank();
    }

    @Test
    void whenGettingDurationFromMethod_thenResultNotNullAndInBoundaries() {
        assertThat(MethodInvocationWithParams.getDurationFromMethod())
                .isNotNull()
                .isBetween(Duration.ofSeconds(MethodInvocationWithParams.MIN),
                        Duration.ofSeconds(MethodInvocationWithParams.MAX));
    }
}