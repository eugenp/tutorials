package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplatifyUnitTest {
    @Test
    void testGetExpressionWithPlaceholder() {
        assertThat(Templatify.getExpressionWithPlaceholder()).isNotBlank()
                .matches(".ight");
    }

    @Test
    void testGetExpression() {
        assertThat(Templatify.getExpression()).isNotBlank()
                .matches(".es.");
    }
}