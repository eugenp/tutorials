package com.baeldung.sorted.operations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InsertElementInSortedArrayUnitTest {

    @Test
    void givenSortedArray_whenElementInserted_thenArrayRemainsSorted() {
        int[] sorted_array = new int[] { 4, 55, 85, 100, 125 };
        int[] expceted_array = new int[] { 4, 55, 85, 90, 100, 125 };
        int valueToInsert = 90;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(sorted_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }

    @Test
    void givenEmptyArray_whenElementInserted_thenElementInserted() {
        int[] empty_array = new int[] {};
        int[] expceted_array = new int[] { 90 };
        int valueToInsert = 90;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(empty_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }

    @Test
    void givenSingleElementArray_whenElementInserted_thenElementInsertedBeforeOrAfterSingleElement() {
        int[] sorted_array = new int[] { 50 };
        int[] expceted_array = new int[] { 50, 90 };
        int valueToInsert = 90;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(sorted_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }

    @Test
    void givenSortedArray_whenDuplicateElementInserted_thenElementInsertedInArray() {
        int[] sorted_array = new int[] { 10, 50, 70 };
        int[] expceted_array = new int[] { 10, 50, 50, 70 };
        int valueToInsert = 50;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(sorted_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }

    @Test
    void givenSortedIntegerArray_whenElementInsertedLessThanFirstArrayElement_thenElementInsertedAtFirstIndex() {
        int[] sorted_array = new int[] { 10, 50, 70 };
        int[] expceted_array = new int[] { 5, 10, 50, 70 };
        int valueToInsert = 5;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(sorted_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }

    @Test
    void givenSortedIntegerArray_whenElementInsertedGreaterThanLastArrayElement_thenElementInsertedAtLastIndex() {
        int[] sorted_array = new int[] { 10, 50, 70 };
        int[] expceted_array = new int[] { 10, 50, 70, 80 };
        int valueToInsert = 80;
        int[] result_array = InsertElementInSortedArray.insertInSortedArray(sorted_array, valueToInsert);
        assertThat(expceted_array).isEqualTo(result_array);
    }
}
