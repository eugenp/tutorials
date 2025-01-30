package com.baeldung.array.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.lang3.ArrayUtils;

import org.junit.jupiter.api.Test;

public class ArrayOperationsUnitTest {

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingPureJavaIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = { 1, 2, 3, 4 };
        int elementToAdd = 5;

        Integer[] destArray = ArrayOperations.addElementUsingPureJava(sourceArray, elementToAdd);

        Integer[] expectedArray = { 1, 2, 3, 4, 5 };
        assertArrayEquals(expectedArray, destArray);
    }

    @Test
    void whenInsertAnElementAtAGivenIndexCalled_thenShiftTheFollowingElementsAndInsertTheElementInArray() {
        int[] expectedArray = { 1, 2, 42, 3, 4 };
        int[] anArray = { 1, 2, 3, 4 };
        int[] outputArray = ArrayOperations.insertAnElementAtAGivenIndex(anArray, 2, 42);

        assertThat(outputArray).containsExactly(expectedArray);
    }

    @Test
    void whenPrependingAnElementUsingInsertAnElementAtAGivenIndex_thenGetExpectedResult() {
        int[] anArray = { 1, 2, 3, 4 };
        int[] expectedArray = { 42, 1, 2, 3, 4 };
        int[] result = ArrayOperations.insertAnElementAtAGivenIndex(anArray, 0, 42);

        assertThat(result).containsExactly(expectedArray);
    }

    @Test
    void whenUsingPrependAnElementToArray_thenGetExpectedResult() {
        int[] anArray = { 1, 2, 3, 4 };
        int[] expectedArray = { 42, 1, 2, 3, 4 };
        int[] result = ArrayOperations.prependAnElementToArray(anArray, 42);

        assertThat(result).containsExactly(expectedArray);
    }

    @Test
    void whenPrependingAnElementUsingArrayUtils_thenGetExpectedResult() {
        int[] anArray = { 1, 2, 3, 4 };
        int[] expectedArray = { 42, 1, 2, 3, 4 };
        int[] result = ArrayUtils.addFirst(anArray, 42);

        assertThat(result).containsExactly(expectedArray);
    }
}