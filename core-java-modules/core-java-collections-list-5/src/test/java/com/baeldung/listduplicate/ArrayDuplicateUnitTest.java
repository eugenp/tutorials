package com.baeldung.listduplicate;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ArrayDuplicateUnitTest {
    @Test
    public void testFindDuplicates() {
        int[] arrayWithDuplicates = {1, 2, 3, 4, 2, 5, 6, 6, 7};
        Set<Integer> expectedDuplicates = new HashSet<>();
        expectedDuplicates.add(2);
        expectedDuplicates.add(6);
        Set<Integer> actualDuplicates = ListDuplicate.findDuplicateInArray(arrayWithDuplicates);
        assertEquals(expectedDuplicates, actualDuplicates);
    }
}
