package com.baeldung.array.access;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RetrieveFirstAndLastElementUnitTest {

    @Test
    void whenRetrieveFirstAndLastElement_thenCorrect() {
        String[] array = { "java", "kotlin", "c", "c#", "go", "python", "rust", "ruby" };
        assertEquals("java", array[0]);
        assertEquals("ruby", array[array.length - 1]);
    }

    @Test
    void whenGetElementFromAnEmptyArray_thenThrowsException() {
        String[] array = new String[] {};

        assertEquals(0, array.length);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {String failing = array[0];});
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {String failing = array[array.length - 1];});
    }
}