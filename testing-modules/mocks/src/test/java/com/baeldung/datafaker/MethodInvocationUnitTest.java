package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MethodInvocationUnitTest {

    @Test
    void testGetNameFromExpression() {
        assertThat(MethodInvocation.getNameFromMethod()).isNotBlank();
    }

    @Test
    void testGetNameFromMethod() {
        assertThat(MethodInvocation.getNameFromExpression()).isNotBlank();
    }
}