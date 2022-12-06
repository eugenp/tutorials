package com.baeldung.sample;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;

import static org.assertj.core.api.Assertions.assertThat;

class JacksonRuntimeHintsUnitTest {

    @Test
    void shouldRegisterSnakeCasePropertyNamingStrategy() {
        // arrange
        final var hints = new RuntimeHints();
        final var expectSnakeCaseHint = RuntimeHintsPredicates
          .reflection()
          .onField(PropertyNamingStrategies.class, "SNAKE_CASE");
        // act
        new JacksonRuntimeHints.PropertyNamingStrategyRegistrar()
          .registerHints(hints, getClass().getClassLoader());
        // assert
        assertThat(expectSnakeCaseHint).accepts(hints);

    }

}
