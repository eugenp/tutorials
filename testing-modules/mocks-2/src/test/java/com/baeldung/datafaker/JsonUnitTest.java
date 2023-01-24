package com.baeldung.datafaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

class JsonUnitTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenGettingJsonExpression_thenResultIsValidJson() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> objectMapper.readTree(Json.getExpression())),
                () -> assertThat(Json.getExpression()).isNotBlank()
        );
    }
}