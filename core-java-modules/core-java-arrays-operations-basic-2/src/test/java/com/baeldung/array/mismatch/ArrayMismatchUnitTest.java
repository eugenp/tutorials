package com.baeldung.array.mismatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

class ArrayMismatchUnitTest {
    
    @Test
    void givenTwoArraysWithACommonPrefix_whenMismatch_thenIndexOfFirstMismatch() {
        int[] firstArray = {1, 2, 3, 4, 5};
        int[] secondArray = {1, 2, 3, 5, 8};
        assertEquals(3, Arrays.mismatch(firstArray, secondArray));
    }
    
    @Test
    void givenTwoIdenticalArrays_whenMismatch_thenMinusOne() {
        int[] firstArray = {1, 2, 3, 4, 5};
        int[] secondArray = {1, 2, 3, 4, 5};
        assertEquals(-1, Arrays.mismatch(firstArray, secondArray));
    }
    
    @Test
    void givenFirstArrayIsAPrefixOfTheSecond_whenMismatch_thenFirstArrayLength() {
        int[] firstArray = {1, 2, 3, 4, 5};
        int[] secondArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(firstArray.length, Arrays.mismatch(firstArray, secondArray));
    }
    
    @Test
    void givenNoCommonPrefix_whenMismatch_thenZero() {
        int[] firstArray = {1, 2, 3, 4, 5};
        int[] secondArray = {9, 8, 7};
        assertEquals(0, Arrays.mismatch(firstArray, secondArray));
    }
    
    @Test
    void givenAtLeastANullArray_whenMismatch_thenThrowsNullPointerException() {
        int[] firstArray = null;
        int[] secondArray = {1, 2, 3, 4, 5};
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, secondArray));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, firstArray));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, firstArray));
    }
    
    @Test
    void givenExactlyOneAnEmptyArray_whenMismatch_thenZero() {
        int[] firstArray = {};
        int[] secondArray = {1, 2, 3};
        assertEquals(0, Arrays.mismatch(firstArray, secondArray));
        assertEquals(0, Arrays.mismatch(secondArray, firstArray));
    }
    
    @Test
    void givenTwoEmptyArrays_whenMismatch_thenMinusOne() {
        assertEquals(-1, Arrays.mismatch(new int[] {}, new int[] {}));
    }
    
    @Test
    void givenTwoSubArraysWithACommonPrefix_whenMismatch_thenIndexOfFirstMismatch() {
        int[] firstArray = {1, 2, 3, 4, 5};
        int[] secondArray = {0, 1, 2, 3, 5, 8};
        assertEquals(3, Arrays.mismatch(firstArray, 0, 4, secondArray, 1, 6));
    }
    
    @Test
    void givenTwoIdenticalSubArrays_whenMismatch_thenMinusOne() {
        int[] firstArray = {0, 0, 1, 2};
        int[] secondArray = {0, 1, 2, 3, 4};
        assertEquals(-1, Arrays.mismatch(firstArray, 2, 4, secondArray, 1, 3));
    }
    
    @Test
    void givenFirstSubArrayIsAPrefixOfTheSecond_whenMismatch_thenFirstArrayLength() {
        int[] firstArray = {2, 3, 4, 5, 4, 3, 2};
        int[] secondArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertEquals(4, Arrays.mismatch(firstArray, 0, 4, secondArray, 2, 9));
    }
    
    @Test
    void givenNoCommonPrefixForSubArrays_whenMismatch_thenZero() {
        int[] firstArray = {0, 0, 0, 0, 0};
        int[] secondArray = {9, 8, 7, 6, 5};
        assertEquals(0, Arrays.mismatch(firstArray, 1, 2, secondArray, 1, 2));
    }
    
    @Test
    void givenAtLeastANullSubArray_whenMismatch_thenThrowsNullPointerException() {
        int[] firstArray = null;
        int[] secondArray = {1, 2, 3, 4, 5};
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, 0, 1, secondArray, 0, 1));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, 0, 1, firstArray, 0, 1));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, 0, 1, firstArray, 0, 1));
    }
    
    @Test
    void givenExactlyOneEmptySubArray_whenMismatch_thenZero() {
        int[] firstArray = {1};
        int[] secondArray = {1, 2, 3};
        assertEquals(0, Arrays.mismatch(firstArray, 0, 0, secondArray, 0, 2));
        assertEquals(0, Arrays.mismatch(firstArray, 0, 1, secondArray, 2, 2));
    }
    
    @Test
    void givenTwoEmptySubArrays_whenMismatch_thenMinusOne() {
        int[] firstArray = {1};
        int[] secondArray = {1, 2, 3};
        assertEquals(-1, Arrays.mismatch(firstArray, 0, 0, secondArray, 2, 2));
    }
    
    @Test
    void givenToIndexGreaterThanFromIndex_whenMismatch_thenThrowsIllegalArgumentException() {
        int[] firstArray = {2, 3, 4, 5, 4, 3, 2};
        int[] secondArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThrows(IllegalArgumentException.class, () -> Arrays.mismatch(firstArray, 4, 2, secondArray, 0, 6));
        assertThrows(IllegalArgumentException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, 6, 0));
    }
    
    @Test
    void givenIllegalIndexes_whenMismatch_thenThrowsArrayIndexOutOfBoundsException() {
        int[] firstArray = {2, 3, 4, 5, 4, 3, 2};
        int[] secondArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, -1, 2, secondArray, 0, 6));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 0, 8, secondArray, 0, 6));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, -5, 0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, 11, 12));
    }
    
    @Test
    void givenTwoStringArraysAndAComparator_whenMismatch_thenIndexOfFirstMismatch() {
        String[] firstArray = {"one", "two", "three"};
        String[] secondArray = {"ONE", "TWO", "FOUR"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(2, Arrays.mismatch(firstArray, secondArray, comparator));
    }
    
    @Test
    void givenTwoIdenticalStringArraysForTheComparator_whenMismatch_thenMinusOne() {
        String[] firstArray = {"one", "two", "three"};
        String[] secondArray = {"ONE", "TWO", "THREE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(-1, Arrays.mismatch(firstArray, secondArray, comparator));
    }
    
    @Test
    void givenFirstStringArrayIsAPrefixOfTheSecondForTheComparator_whenMismatch_thenFirstArrayLength() {
        String[] firstArray = {"one", "two", "three"};
        String[] secondArray = {"ONE", "TWO", "THREE", "FOUR", "FIVE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(firstArray.length, Arrays.mismatch(firstArray, secondArray, comparator));
    }
    
    @Test
    void givenNoCommonPrefixForTheComparator_whenMismatch_thenZero() {
        String[] firstArray = {"one", "two", "three"};
        String[] secondArray = {"six", "seven", "eight"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(0, Arrays.mismatch(firstArray, secondArray, comparator));
    }
    
    @Test
    void givenAtLeastANullArrayOrNullComparator_whenMismatch_thenThrowsNullPointerException() {
        String[] firstArray = null;
        String[] secondArray = {"one"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, secondArray, comparator));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, firstArray, comparator));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, secondArray, null));
    }
    
    @Test
    void givenExactlyOneAnEmptyArrayAndAComparator_whenMismatch_thenZero() {
        String[] firstArray = {};
        String[] secondArray = {"one"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(0, Arrays.mismatch(firstArray, secondArray, comparator));
        assertEquals(0, Arrays.mismatch(secondArray, firstArray, comparator));
    }
    
    @Test
    void givenTwoEmptyStringArraysForTheComparator_whenMismatch_thenMinusOne() {
        assertEquals(-1, Arrays.mismatch(new String[] {}, new String[] {}, String.CASE_INSENSITIVE_ORDER));
    }
    
    @Test
    void givenTwoStringSubarraysAndAComparator_whenMismatch_thenIndexOfFirstMismatch() {
        String[] firstArray = {"one", "two", "three", "four"};
        String[] secondArray = {"ZERO", "ONE", "TWO", "FOUR", "EIGHT", "SIXTEEN"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(2, Arrays.mismatch(firstArray, 0, 4, secondArray, 1, 3, comparator));
    }
   
    @Test
    void givenTwoIdenticalStringSubArraysForTheComparator_whenMismatch_thenMinusOne() {
        String[] firstArray = {"zero", "zero", "one", "two"};
        String[] secondArray = {"zero", "one", "two", "three", "four"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(-1, Arrays.mismatch(firstArray, 2, 4, secondArray, 1, 3, comparator));
    }
    
    @Test
    void givenFirstSubArrayIsAPrefixOfTheSecondForTheComparator_whenMismatch_thenFirstArrayLength() {
        String[] firstArray = {"two", "three", "four", "five", "four", "three", "two"};
        String[] secondArray = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "EIGHT", "NINE", "TEN"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(4, Arrays.mismatch(firstArray, 0, 4, secondArray, 2, 9, comparator));
    }
    
    @Test
    void givenNoCommonPrefixForSubArraysForTheComparator_whenMismatch_thenZero() {
        String[] firstArray = {"zero", "one"};
        String[] secondArray = {"TEN", "ELEVEN", "TWELVE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(0, Arrays.mismatch(firstArray, 1, 2, secondArray, 1, 2, comparator));
    }
    
    @Test
    void givenAtLeastANullSubArrayOrNullComparator_whenMismatch_thenThrowsNullPointerException() {
        String[] firstArray = null;
        String[] secondArray = {"ONE", "TWO", "THREE", "FOUR", "FIVE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, 0, 1, secondArray, 0, 1, comparator));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, 0, 1, firstArray, 0, 1, comparator));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(firstArray, 0, 1, firstArray, 0, 1, comparator));
        assertThrows(NullPointerException.class, () -> Arrays.mismatch(secondArray, 0, 1, secondArray, 0, 1, null));
    }
    
    @Test
    void givenExactlyOneEmptySubArrayAndAComparator_whenMismatch_thenZero() {
        String[] firstArray = {"one"};
        String[] secondArray = {"ONE", "TWO", "THREE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(0, Arrays.mismatch(firstArray, 0, 0, secondArray, 0, 2, comparator));
        assertEquals(0, Arrays.mismatch(firstArray, 0, 1, secondArray, 2, 2, comparator));
    }
    
    @Test
    void givenTwoEmptySubArraysAndAComparator_whenMismatch_thenMinusOne() {
        String[] firstArray = {"one"};
        String[] secondArray = {"ONE", "TWO", "THREE"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertEquals(-1, Arrays.mismatch(firstArray, 0, 0, secondArray, 2, 2, comparator));
    }
    
    @Test
    void givenToIndexGreaterThanFromIndexAndAComparator_whenMismatch_thenThrowsIllegalArgumentException() {
        String[] firstArray = {"two", "three", "four", "five", "four", "three", "two"};
        String[] secondArray = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "EIGHT", "NINE", "TEN"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertThrows(IllegalArgumentException.class, () -> Arrays.mismatch(firstArray, 4, 2, secondArray, 0, 6, comparator));
        assertThrows(IllegalArgumentException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, 6, 0, comparator));
    }
    
    @Test
    void givenIllegalIndexesAndAComparator_whenMismatch_thenThrowsArrayIndexOutOfBoundsException() {
        String[] firstArray = {"two", "three", "four", "five", "four", "three", "two"};
        String[] secondArray = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "EIGHT", "NINE", "TEN"};
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, -1, 2, secondArray, 0, 6, comparator));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 0, 8, secondArray, 0, 6, comparator));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, -5, 0, comparator));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.mismatch(firstArray, 2, 3, secondArray, 11, 12, comparator));
    }

}
