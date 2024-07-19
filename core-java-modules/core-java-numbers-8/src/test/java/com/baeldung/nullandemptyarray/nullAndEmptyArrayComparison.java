package com.baeldung.nullandemptyarray;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class nullAndEmptyArrayComparison {

    @Test
    public void givenNullAndEmptyArray_whenCompare_thenNotEqual() {
        int[] nullArray = null;
        int[] emptyArray = new int[0];
        assertFalse(emptyArray.equals(nullArray));
        assertFalse(emptyArray == nullArray);
    }

}
