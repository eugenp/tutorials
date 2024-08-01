package com.baeldung.nullandemptyarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NullAndEmptyArrayTest {

    @Test
    public void givenNullArray_whenAccessLength_thenThrowsNullPointerException() {
        int[] nullArray = null;
        assertThrows(NullPointerException.class, () -> {
            int length = nullArray.length; 
        });
    }

    @Test
    public void givenEmptyArray_whenCheckLength_thenReturnsZero() {
        int[] emptyArray = new int[0];
        assertEquals(0, emptyArray.length);
    }
}
