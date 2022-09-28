package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegexifyUnitTest {

    @Test
    void getMethodExpression() {
        assertThat(Regexify.getMethodExpression()).isNotBlank()
                .matches("[A-D]{4,10}");
    }

    @Test
    void getExpression() {
        assertThat(Regexify.getExpression()).isNotBlank()
                .matches("(hello|bye|hey)");
    }
}