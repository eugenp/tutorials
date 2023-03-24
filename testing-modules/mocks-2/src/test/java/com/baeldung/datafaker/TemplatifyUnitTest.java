package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplatifyUnitTest {
    @Test
    void whenGettingPlaceholderExpression_thenResultNotBlankAndMatchesRegex() {
        assertThat(Templatify.getExpressionWithPlaceholder()).isNotBlank()
                .matches(".ight");
    }

    @Test
    void whenGettingExpression_thenResultNotBlankAndMatchesRegex() {
        assertThat(Templatify.getExpression()).isNotBlank()
                .matches(".es.");
    }
}