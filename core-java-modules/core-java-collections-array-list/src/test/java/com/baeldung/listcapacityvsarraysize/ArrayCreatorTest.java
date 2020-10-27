package com.baeldung.listcapacityvsarraysize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayCreatorTest {

    @Test
    void whenSizeOfAnArrayIsNonZero_thenReturnNewArrayOfGivenSize() {
        Integer[] array = ArrayCreator.createIntegerArrayOf(10);
        assertEquals(10, array.length);
    }

    @Test
    void whenSizeOfAnArrayIsLessThanZero_thenThrowException() {
        assertThrows(NegativeArraySizeException.class, () -> ArrayCreator.createIntegerArrayOf(-1));
    }
}