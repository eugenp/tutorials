package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionUnitTest {
    @Test
    void testGetThirdExpression() {
        assertThat(Option.getThirdExpression()).isNotBlank()
                .matches("(Hi|Hello|Hey)");
    }

    @Test
    void testGetSecondExpression() {
        assertThat(Option.getSecondExpression()).isNotBlank()
                .matches("(1|2|3|4|\\*)");
    }

    @Test
    void testGetFirstExpression() {
        assertThat(Option.getFirstExpression()).isNotBlank()
                .matches("(Hi|Hello|Hey)");
    }
}