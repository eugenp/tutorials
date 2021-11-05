package com.baeldung.optional;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionalUnitTest {

    private final String stringToTest = "testString";

    @Test
    public void givenOptionalWithValue_whenAssertThatIsNotEmpty_thenOk() {
        assertThat(optionalWithValueMethodToTest()).isNotEmpty();
    }

    @Test
    public void givenOptionalWithValue_whenAssertThatHasValue_thenOk() {
        assertThat(optionalWithValueMethodToTest()).hasValue(stringToTest);
    }

    @Test
    public void givenOptionalWithValue_whenAssertEqualsOptionalObject_thenOk() {
        assertEquals(Optional.of(stringToTest), optionalWithValueMethodToTest());
    }

    @Test
    public void givenOptionalWithValue_whenAssertEqualsGet_thenOk() {
        Optional<String> optional = optionalWithValueMethodToTest();
        assertEquals(stringToTest, optional.get());
    }

    @Test
    public void givenOptionalWithValue_whenAssertTrueIsPresentAndGet_thenOk() {
        Optional<String> optional = optionalWithValueMethodToTest();
        assertTrue(optional.isPresent() && stringToTest.equals(optional.get()));
    }

    @Test
    public void givenOptionalWithValue_whenIsPresentAndGetSplit_thenOk() {
        Optional<String> optional = optionalWithValueMethodToTest();

        assertTrue(optional.isPresent());
        assertEquals(stringToTest, optional.get());
    }

    @Test
    public void givenEmptyOptional_whenAssertThatIsEmpty_thenOk() {
        assertThat(optionalIsEmptyMethodToTest()).isEmpty();
    }

    @Test
    public void givenEmptyOptional_whenAssertIsNotPresent_thenOk() {
        Optional<String> optional = optionalIsEmptyMethodToTest();
        assertFalse(optional.isPresent());
    }

    private Optional<String> optionalWithValueMethodToTest() {
        return Optional.of(stringToTest);
    }

    private Optional<String> optionalIsEmptyMethodToTest() {
        return Optional.empty();
    }
}
