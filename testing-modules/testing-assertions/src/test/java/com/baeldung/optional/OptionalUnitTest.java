package com.baeldung.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionalUnitTest {

    private static final String optionalReturnString = "optionalReturnString";

    @Test
    public void givenOptionalWithValue_whenAssertThatIsNotEmpty_thenOk() {
        assertThat(Optional.of(optionalReturnString)).isNotEmpty();
    }

    @Test
    public void givenOptionalWithValue_whenAssertThatHasValue_thenOk() {
        assertThat(Optional.of(optionalReturnString)).hasValue(optionalReturnString);
    }

    @Test
    public void givenOptionalWithValue_whenAssertEqualsOptionalObject_thenOk() {
        assertEquals(Optional.of(optionalReturnString), Optional.of(optionalReturnString));
    }

    @Test
    public void givenOptionalWithValue_whenAssertEqualsGet_thenOk() {
        Optional<String> optional = Optional.of(optionalReturnString);
        assertEquals(optionalReturnString, optional.get());
    }

    @Test
    public void givenOptionalWithValue_whenIsPresentAndGetSplit_thenOk() {
        Optional<String> optional = Optional.of(optionalReturnString);

        assertTrue(optional.isPresent());
        assertEquals(optionalReturnString, optional.get());
    }

    @Test
    public void givenEmptyOptional_whenAssertThatIsEmpty_thenOk() {
        Optional<String> emptyOptional = Optional.empty();
        assertThat(emptyOptional).isEmpty();
    }

    @Test
    public void givenEmptyOptional_whenAssertIsNotPresent_thenOk() {
        Optional<String> emptyOptional = Optional.empty();
        assertFalse(emptyOptional.isPresent());
    }
}
