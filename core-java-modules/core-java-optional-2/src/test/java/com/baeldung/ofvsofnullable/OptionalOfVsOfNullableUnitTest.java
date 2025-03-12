package com.baeldung.ofvsofnullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalOfVsOfNullableUnitTest {

    @Test
    void givenNonNullReference_whenUsingOptionalOf_thenObtainOptional() {
        String s = "no null here";
        assertThat(Optional.of(s)).isNotEmpty()
            .hasValue("no null here");
    }

    @Test
    void givenNullReference_whenUsingOptionalOf_thenNullPointerExceptionThrown() {
        String s = null;
        assertThatThrownBy(() -> Optional.of(s)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenNullReference_whenUsingOptionalOfNullable_thenObtainOptional() {
        String s = null;
        assertThat(Optional.ofNullable(s)).isEmpty();
    }
}