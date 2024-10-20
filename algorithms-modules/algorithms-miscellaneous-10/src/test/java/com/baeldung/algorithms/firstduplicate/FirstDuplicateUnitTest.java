package com.baeldung.algorithms.firstduplicate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FirstDuplicateUnitTest {

    @Test
    public void givenArray_whenUsingBruteForce_thenFindFirstDuplicate() {
        FirstDuplicate firstDuplicate = new FirstDuplicate();

        assertEquals(4, firstDuplicate.firstDuplicateBruteForce(new int[] { 2, 1, 3, 5, 3, 2 }));
        assertEquals(-1, firstDuplicate.firstDuplicateBruteForce(new int[] { 1, 2, 3, 4, 5 }));
        assertEquals(2, firstDuplicate.firstDuplicateBruteForce(new int[] { 2, 1, 1, 2 }));
        assertEquals(1, firstDuplicate.firstDuplicateBruteForce(new int[] { 1, 1 }));
        assertEquals(-1, firstDuplicate.firstDuplicateBruteForce(new int[] {}));
    }

    @Test
    public void givenArray_whenUsingHashSet_thenFindFirstDuplicate() {
        FirstDuplicate firstDuplicate = new FirstDuplicate();

        assertEquals(4, firstDuplicate.firstDuplicateHashSet(new int[] { 2, 1, 3, 5, 3, 2 }));
        assertEquals(-1, firstDuplicate.firstDuplicateHashSet(new int[] { 1, 2, 3, 4, 5 }));
        assertEquals(2, firstDuplicate.firstDuplicateHashSet(new int[] { 2, 1, 1, 2 }));
        assertEquals(1, firstDuplicate.firstDuplicateHashSet(new int[] { 1, 1 }));
        assertEquals(-1, firstDuplicate.firstDuplicateHashSet(new int[] {}));
    }

    @Test
    public void givenArray_whenUsingArrayIndexing_thenFindFirstDuplicate() {
        FirstDuplicate firstDuplicate = new FirstDuplicate();

        assertEquals(4, firstDuplicate.firstDuplicateArrayIndexing(new int[] { 2, 1, 3, 5, 3, 2 }));
        assertEquals(-1, firstDuplicate.firstDuplicateArrayIndexing(new int[] { 1, 2, 3, 4, 5 }));
        assertEquals(2, firstDuplicate.firstDuplicateArrayIndexing(new int[] { 2, 1, 1, 2 }));
        assertEquals(1, firstDuplicate.firstDuplicateArrayIndexing(new int[] { 1, 1 }));
        assertEquals(-1, firstDuplicate.firstDuplicateArrayIndexing(new int[] {}));
    }
}

