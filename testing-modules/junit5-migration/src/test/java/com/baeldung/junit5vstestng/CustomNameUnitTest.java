package com.baeldung.junit5vstestng;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CustomNameUnitTest {

    @ParameterizedTest
    @ValueSource(strings = { "Hello", "World" })
    @DisplayName("Test Method to check that the inputs are not nullable")
    void givenString_TestNullOrNot(String word) {
        assertNotNull(word);
    }
}
