package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionUnitTest {
    @Test
    void whenGettingThirdExpression_thenResultNotBlankAndMatchesRegex() {
        assertThat(Option.getThirdExpression()).isNotBlank()
                .matches("(Hi|Hello|Hey)");
    }

    @Test
    void whenGettingSecondExpression_thenResultNotBlankAndMatchesRegex() {
        assertThat(Option.getSecondExpression()).isNotBlank()
                .matches("(1|2|3|4|\\*)");
    }

    @Test
    void whenGettingFirstExpression_thenResultNotBlankAndMatchesRegex() {
        assertThat(Option.getFirstExpression()).isNotBlank()
                .matches("(Hi|Hello|Hey)");
    }
}