package com.baeldung.array;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveElementFromAnArrayUnitTest {

    private final RemoveElementFromAnArray sut = new RemoveElementFromAnArray();
    private final int[] inputArray = new int[] { 40, 10, 20, 30, 40, 50 };

    @Test
    void testRemoveAnElementWithAGivenIndex() {
        int index = 2;
        int[] modifiedArray = sut.removeAnElementWithAGivenIndex(inputArray, index);

        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[index]));
    }

    @Test
    void testRemoveAllElementsWithGivenIndices() {
        int first = 0;
        int last = inputArray.length - 1;
        int[] modifiedArray = sut.removeAllElementsWithGivenIndices(inputArray, first, last);

        assertFalse(ArrayUtils.contains(modifiedArray, inputArray[first]) && ArrayUtils.contains(modifiedArray, inputArray[last]));
    }

    @Test
    void testRemoveElement_WhenArrayIsNull_ThrowsIndexOutOfBoundEx() {
        int index = 2;

        assertThrows(IndexOutOfBoundsException.class, () -> {
            sut.removeAnElementWithAGivenIndex(null, index);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            sut.removeAllElementsWithGivenIndices(null, index);
        });
    }

    @Test
    void testRemoveFirstOccurrenceOfGivenElement() {
        int element = 40;
        int[] modifiedArray = sut.removeFirstOccurrenceOfGivenElement(inputArray, element);

        int indexInInputArray = ArrayUtils.indexOf(inputArray, element);
        int indexInModifiedArray = ArrayUtils.indexOf(modifiedArray, element);
        assertFalse(indexInInputArray == indexInModifiedArray);
    }

    @Test
    void testRemoveAllGivenElements() {
        int duplicateElement = 40;
        int[] elements = new int[] { duplicateElement, 10, 50 };
        int[] modifiedArray = sut.removeAllGivenElements(inputArray, elements);

        assertTrue(ArrayUtils.contains(modifiedArray, duplicateElement));
        assertFalse(ArrayUtils.contains(modifiedArray, elements[1]));
        assertFalse(ArrayUtils.contains(modifiedArray, elements[2]));
    }

    @Test
    void testRemoveAllOccurrencesOfAGivenElement() {
        int element = 40;
        int[] modifiedArray = sut.removeAllOccurrencesOfAGivenElement(inputArray, element);

        assertFalse(ArrayUtils.contains(modifiedArray, element));
    }

    @Test
    void testRemoveElement_WhenArrayIsNull_ReturnsNull() {
        int element = 20;

        assertEquals(null, sut.removeFirstOccurrenceOfGivenElement(null, element));
        assertEquals(null, sut.removeAllGivenElements(null, element));
        assertEquals(null, sut.removeAllOccurrencesOfAGivenElement(null, element));

    }

}
