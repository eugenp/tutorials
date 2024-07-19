package com.baeldung.nullandemptyarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmptyArrayUnitTest {

    @Test
    public void givenEmptyArray_whenCheckLength_thenReturnsZero() {
        int[] emptyArray = new int[0];
        assertEquals(0, emptyArray.length);
    }
}
