package com.baeldung.array.remove;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

class RemoveElementFromAnArrayUnitTest {

    private final RemoveElementFromAnArray sut = new RemoveElementFromAnArray();
    private final int[] inputArray = new int[] { 40, 10, 20, 30, 40, 50 };

    @Test
    void givenIndex_whenUsingApacheCommonsLang_thenRemoveLastElement() {
        int lastElementIndex = inputArray.length - 1;
        int[] modifiedArray = sut.removeAnElementWithAGivenIndex(inputArray, lastElementIndex);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[lastElementIndex]));
    }

    @Test
    void givenIndices_whenUsingApacheCommonsLang_thenRemoveAllElements() {
        int first = 0;
        int lastElementIndex = inputArray.length - 1;
        int[] modifiedArray = sut.removeAllElementsWithGivenIndices(inputArray, first, lastElementIndex);
        assertEquals(modifiedArray.length, inputArray.length - 2);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[first]) && ArrayUtils.contains(modifiedArray, inputArray[lastElementIndex]));
    }


    @Test
    void givenElement_whenUsingApacheCommonsLang_thenRemoveItsFirstOccurrence() {
        int element = 40;
        int[] modifiedArray = sut.removeFirstOccurrenceOfGivenElement(inputArray, element);

        int indexInInputArray = ArrayUtils.indexOf(inputArray, element);
        int indexInModifiedArray = ArrayUtils.indexOf(modifiedArray, element);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertNotEquals(indexInInputArray, indexInModifiedArray);
    }


    @Test
    void givenElements_whenUsingApacheCommonsLang_thenRemoveAllGivenElements() {
        int duplicateElement = 40;
        int[] elements = new int[] { duplicateElement, 10, 50 };
        int[] modifiedArray = sut.removeAllGivenElements(inputArray, elements);
        assertEquals(modifiedArray.length, inputArray.length - 3);
        assertTrue(ArrayUtils.contains(modifiedArray, duplicateElement));
        assertFalse(ArrayUtils.contains(modifiedArray, elements[1]));
        assertFalse(ArrayUtils.contains(modifiedArray, elements[2]));
    }

    @Test
    void givenElements_whenUsingCopyOf_thenRemoveLastElement() {
        int[] modifiedArray = sut.removeLastElementUsingCopyOfMethod(inputArray);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[inputArray.length-1]));
    }

    @Test
    void givenElements_whenUsingCopyOfRange_thenRemoveLastElement() {
        int[] modifiedArray = sut.removeLastElementUsingCopyOfRangeMethod(inputArray);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[inputArray.length-1]));
    }

    @Test
    void givenElements_whenUsingArrayCopyMethod_thenRemoveLastElement() {
        int[] resultArray = new int[inputArray.length - 1];
        int[] modifiedArray = sut.removeLastElementUsingArrayCopyMethod(inputArray, resultArray);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[inputArray.length-1]));
    }

    @Test
    void givenElements_whenUsingIntStreamRange_thenRemoveLastElement() {
        int[] modifiedArray = sut.removeLastElementUsingIntStreamRange(inputArray);
        assertEquals(modifiedArray.length, inputArray.length - 1);
        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[inputArray.length-1]));
    }
}
