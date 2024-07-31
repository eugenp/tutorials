package com.baeldung.commons.lang3;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayUtilsUnitTest {
    @Test
    public void givenArray_whenAddingElementAtSpecifiedPosition_thenCorrect() {
        int[] oldArray = { 2, 3, 4, 5 };
        int[] newArray = ArrayUtils.add(oldArray, 0, 1);
        int[] expectedArray = { 1, 2, 3, 4, 5 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenAddingElementAtTheEnd_thenCorrect() {
        int[] oldArray = { 2, 3, 4, 5 };
        int[] newArray = ArrayUtils.add(oldArray, 1);
        int[] expectedArray = { 2, 3, 4, 5, 1 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenAddingAllElementsAtTheEnd_thenCorrect() {
        int[] oldArray = { 0, 1, 2 };
        int[] newArray = ArrayUtils.addAll(oldArray, 3, 4, 5);
        int[] expectedArray = { 0, 1, 2, 3, 4, 5 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenRemovingElementAtSpecifiedPosition_thenCorrect() {
        int[] oldArray = { 1, 2, 3, 4, 5 };
        int[] newArray = ArrayUtils.remove(oldArray, 1);
        int[] expectedArray = { 1, 3, 4, 5 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenRemovingAllElementsAtSpecifiedPositions_thenCorrect() {
        int[] oldArray = { 1, 2, 3, 4, 5 };
        int[] newArray = ArrayUtils.removeAll(oldArray, 1, 3);
        int[] expectedArray = { 1, 3, 5 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenRemovingAnElement_thenCorrect() {
        int[] oldArray = { 1, 2, 3, 3, 4 };
        int[] newArray = ArrayUtils.removeElement(oldArray, 3);
        int[] expectedArray = { 1, 2, 3, 4 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenRemovingElements_thenCorrect() {
        int[] oldArray = { 1, 2, 3, 3, 4 };
        int[] newArray = ArrayUtils.removeElements(oldArray, 2, 3, 5);
        int[] expectedArray = { 1, 3, 4 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenRemovingAllElementOccurences_thenCorrect() {
        int[] oldArray = { 1, 2, 2, 2, 3 };
        int[] newArray = ArrayUtils.removeAllOccurences(oldArray, 2);
        int[] expectedArray = { 1, 3 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenCheckingExistingElement_thenCorrect() {
        int[] array = { 1, 3, 5, 7, 9 };
        boolean evenContained = ArrayUtils.contains(array, 2);
        boolean oddContained = ArrayUtils.contains(array, 7);
        assertEquals(false, evenContained);
        assertEquals(true, oddContained);
    }

    @Test
    public void givenArray_whenReversingElementsWithinARange_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.reverse(originalArray, 1, 4);
        int[] expectedArray = { 1, 4, 3, 2, 5 };
        assertArrayEquals(expectedArray, originalArray);
    }

    @Test
    public void givenArray_whenReversingAllElements_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.reverse(originalArray);
        int[] expectedArray = { 5, 4, 3, 2, 1 };
        assertArrayEquals(expectedArray, originalArray);
    }

    @Test
    public void givenArray_whenShiftingElementsWithinARange_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.shift(originalArray, 1, 4, 1);
        int[] expectedArray = { 1, 4, 2, 3, 5 };
        assertArrayEquals(expectedArray, originalArray);
    }

    @Test
    public void givenArray_whenShiftingAllElements_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.shift(originalArray, 1);
        int[] expectedArray = { 5, 1, 2, 3, 4 };
        assertArrayEquals(expectedArray, originalArray);
    }

    @Test
    public void givenArray_whenExtractingElements_thenCorrect() {
        int[] oldArray = { 1, 2, 3, 4, 5 };
        int[] newArray = ArrayUtils.subarray(oldArray, 2, 7);
        int[] expectedArray = { 3, 4, 5 };
        assertArrayEquals(expectedArray, newArray);
    }

    @Test
    public void givenArray_whenSwapingElementsWithinARange_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.swap(originalArray, 0, 3, 2);
        int[] expectedArray = { 4, 5, 3, 1, 2 };
        assertArrayEquals(expectedArray, originalArray);
    }

    @Test
    public void givenArray_whenSwapingElementsAtSpecifiedPositions_thenCorrect() {
        int[] originalArray = { 1, 2, 3, 4, 5 };
        ArrayUtils.swap(originalArray, 0, 3);
        int[] expectedArray = { 4, 2, 3, 1, 5 };
        assertArrayEquals(expectedArray, originalArray);
    }
}
