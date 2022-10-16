package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodInvocationUnitTest {

    @Test
    void whenGettingNameFromExpression_thenResultNotEmpty() {
        assertThat(MethodInvocation.getNameFromMethod()).isNotBlank();
    }

    @Test
    void whenGettingNameFromMethod_thenResultNotEmpty() {
        assertThat(MethodInvocation.getNameFromExpression()).isNotBlank();
    }
}