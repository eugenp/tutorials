package com.baeldung.nullandempty;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class NullAndEmptyStringUnitTest {

    @Test
    void givenANullAndEmptyString_whenUsingStringMethods_thenShouldGetExpectedResult() {
        String nullString = null;
        String emptyString = "";
        assertTrue(emptyString.equals(""));
        assertThrows(NullPointerException.class, () -> nullString.length());
    }

    @Test
    void givenANullAndEmptyString_whenCheckingEquality_thenShouldGetExpectedResult() {
        String nullString = null;
        String emptyString = "";
        assertFalse(emptyString.equals(nullString));
        assertFalse(emptyString == nullString);
    }

}
