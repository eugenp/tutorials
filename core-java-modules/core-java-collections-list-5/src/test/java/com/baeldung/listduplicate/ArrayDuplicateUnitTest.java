package com.baeldung.listduplicate;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ArrayDuplicateUnitTest {
    private static final Integer[] arrayWithDuplicates = {1, 2, 3, 4, 2, 5, 6, 6, 7};
    @Test
    public void givenArray_whenUsingSetWithForLoop_thenReturnExpectedDuplicates() {
        Set<Integer> expectedDuplicates = new HashSet<>();
        expectedDuplicates.add(2);
        expectedDuplicates.add(6);
        Set<Integer> actualDuplicates = ListDuplicate.findDuplicateInArrayWithForLoop(arrayWithDuplicates);
        assertEquals(expectedDuplicates, actualDuplicates);
    }

    @Test
    public void givenArray_whenUsingSetWithStream_thenReturnExpectedDuplicates() {
        Set<Integer> expectedDuplicates = new HashSet<>();
        expectedDuplicates.add(2);
        expectedDuplicates.add(6);
        Set<Integer> actualDuplicates = ListDuplicate.findDuplicateInArrayWithStream(arrayWithDuplicates);
        assertEquals(expectedDuplicates, actualDuplicates);
    }
}
