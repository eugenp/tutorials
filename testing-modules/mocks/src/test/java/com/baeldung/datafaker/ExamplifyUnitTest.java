package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExamplifyUnitTest {

    @Test
    void testGetNumberExpression() {
        assertAll(
                () -> assertThat(Examplify.getNumberExpression()).isNotBlank(),
                () -> assertThat(Examplify.getNumberExpression()).matches("\\d{3}-\\d{3}-\\d{3}")
        );
    }

    @Test
    void testGetExpression() {
        assertAll(
                () -> assertThat(Examplify.getExpression()).isNotBlank(),
                () -> assertThat(Examplify.getExpression())
                        .matches("[A-Z][a-z]{2} [a-z]{2} [a-z]{3} [A-Z][a-z]{2}")
        );
    }
}