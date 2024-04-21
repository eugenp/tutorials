package com.baeldung.castingandstringvalueof;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CastingToStringVsStringValueOfUnitTest {

    @Test
    void whenCastingStringAndCallingValueOf_thenCorrect() {
        Object obj = "Baeldung is awesome!";

        String castResult = (String) obj;
        assertEquals("Baeldung is awesome!", castResult);

        String valueOfResult = String.valueOf(obj);
        assertEquals("Baeldung is awesome!", valueOfResult);
    }

    @Test
    void whenCastingNonStringAndCallingValueOf_thenGetExpectedResult() {
        Object obj = 42;
        assertThrows(ClassCastException.class, () -> {String castResult = (String) obj;});

        String valueOfResult = String.valueOf(obj);
        assertEquals("42", valueOfResult);

        Object obj2 = List.of("Baeldung", "is", "awesome");
        assertThrows(ClassCastException.class, () -> {String castResult = (String) obj2;});

        valueOfResult = String.valueOf(obj2);
        assertEquals("[Baeldung, is, awesome]", valueOfResult);
    }

    @Test
    void whenObjectIsNullCastingAndCallingValueOf_thenGetExpectedResult() {
        Object obj = null;
        String castResult = (String) obj;
        assertNull(castResult);

        String valueOfResult = String.valueOf(obj);
        assertNotNull(valueOfResult);
        assertEquals("null", valueOfResult);
    }
}