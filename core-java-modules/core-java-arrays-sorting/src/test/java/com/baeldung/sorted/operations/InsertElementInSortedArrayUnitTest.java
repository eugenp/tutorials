package com.baeldung.sorted.operations;

import static com.baeldung.sorted.operations.InsertElementInSortedArray.insertInSortedArray;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InsertElementInSortedArrayUnitTest {

    @Test
    void givenSortedArray_whenElementInserted_thenArrayRemainsSorted() {
        int[] sortedArray = new int[] { 4, 55, 85, 100, 125 };
        int[] expcetedArray = new int[] { 4, 55, 85, 90, 100, 125 };
        int valueToInsert = 90;
        int[] resultArray = insertInSortedArray(sortedArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }

    @Test
    void givenEmptyArray_whenElementInserted_thenElementInserted() {
        int[] emptyArray = new int[] {};
        int[] expcetedArray = new int[] { 90 };
        int valueToInsert = 90;
        int[] resultArray = insertInSortedArray(emptyArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }

    @Test
    void givenSingleElementArray_whenElementInserted_thenElementInsertedBeforeOrAfterSingleElement() {
        int[] sortedArray = new int[] { 50 };
        int[] expcetedArray = new int[] { 50, 90 };
        int valueToInsert = 90;
        int[] resultArray = insertInSortedArray(sortedArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }

    @Test
    void givenSortedArray_whenDuplicateElementInserted_thenElementInsertedInArray() {
        int[] sortedArray = new int[] { 10, 50, 70 };
        int[] expcetedArray = new int[] { 10, 50, 50, 70 };
        int valueToInsert = 50;
        int[] resultArray = insertInSortedArray(sortedArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }

    @Test
    void givenSortedIntegerArray_whenElementInsertedLessThanFirstArrayElement_thenElementInsertedAtFirstIndex() {
        int[] sortedArray = new int[] { 10, 50, 70 };
        int[] expcetedArray = new int[] { 5, 10, 50, 70 };
        int valueToInsert = 5;
        int[] resultArray = insertInSortedArray(sortedArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }

    @Test
    void givenSortedIntegerArray_whenElementInsertedGreaterThanLastArrayElement_thenElementInsertedAtLastIndex() {
        int[] sortedArray = new int[] { 10, 50, 70 };
        int[] expcetedArray = new int[] { 10, 50, 70, 80 };
        int valueToInsert = 80;
        int[] resultArray = insertInSortedArray(sortedArray, valueToInsert);
        assertThat(expcetedArray).isEqualTo(resultArray);
    }
}
