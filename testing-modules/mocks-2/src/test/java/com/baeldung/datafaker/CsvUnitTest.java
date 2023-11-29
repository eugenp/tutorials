package com.baeldung.datafaker;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CsvUnitTest {

    @Test
    void whenGettingFirstExpression_thenResultNotEmptyAndOfCorrectSizeAndFormat() {
        assertAll(
                () -> assertThat(Csv.getFirstExpression()).isNotBlank(),
                () -> assertThat(Csv.getFirstExpression().split("\n")).hasSize(5),
                () -> assertThat(Csv.getFirstExpression().split("\n")[0]).isEqualTo("\"name_column\",\"last_name_column\"")
        );
    }

    @Test
    void whenGettingSecondExpression_thenResultNotEmptyAndOfCorrectSizeAndFormat() {
        assertAll(
                () -> assertThat(Csv.getFirstExpression()).isNotBlank(),
                () -> assertThat(Csv.getFirstExpression().split("\n")).hasSize(5),
                () -> assertThat(Csv.getFirstExpression().split("\n")[0]).isEqualTo("\"name_column\",\"last_name_column\"")
        );
    }
}