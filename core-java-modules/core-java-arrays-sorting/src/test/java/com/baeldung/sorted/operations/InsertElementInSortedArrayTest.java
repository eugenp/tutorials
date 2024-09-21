package com.baeldung.sorted.operations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class InsertElementInSortedArrayTest {

    private static final int[] SORTED_ARRAY = new int[] {4,55,85,100,125};
    
    @Test
    public void givenSortedArray_whenElementInserted_thenArrayRemainsSorted(){
        int valueToInsert = 90;
        int[] result = InsertElementInSortedArray.insertInSortedArr(SORTED_ARRAY, valueToInsert);
        int expectedIndex = 3;
        assertThat(result[expectedIndex]).isEqualTo(valueToInsert);
    }
}
