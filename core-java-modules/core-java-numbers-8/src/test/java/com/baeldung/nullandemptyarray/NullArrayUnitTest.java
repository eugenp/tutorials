package com.baeldung.nullandemptyarray;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class NullArrayUnitTest {

    @Test
    public void givenNullArray_whenAccessLength_thenThrowsNullPointerException() {
        int[] nullArray = null;
        assertThrows(NullPointerException.class, () -> {
            int length = nullArray.length;
        });
    }
}
