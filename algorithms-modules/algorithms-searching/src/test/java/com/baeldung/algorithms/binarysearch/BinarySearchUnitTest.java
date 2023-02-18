package com.baeldung.algorithms.binarysearch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class BinarySearchUnitTest {

    int[] sortedArray = { 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9 };
    int key = 6;
    int expectedIndexForSearchKey = 7;
    int low = 0;
    int high = sortedArray.length - 1;
    List<Integer> sortedList = Arrays.asList(0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 9);
    
    @Test
    void givenASortedArrayOfIntegers_whenBinarySearchRunIterativelyForANumber_thenGetIndexOfTheNumber() {
        BinarySearch binSearch = new BinarySearch();
        assertEquals(expectedIndexForSearchKey, binSearch.runBinarySearchIteratively(sortedArray, key, low, high));
    }

    @Test
    void givenASortedArrayOfIntegers_whenBinarySearchRunRecursivelyForANumber_thenGetIndexOfTheNumber() {
        BinarySearch binSearch = new BinarySearch();
        assertEquals(expectedIndexForSearchKey, binSearch.runBinarySearchRecursively(sortedArray, key, low, high));
    }
    
    @Test
    void givenASortedArrayOfIntegers_whenBinarySearchRunUsingArraysClassStaticMethodForANumber_thenGetIndexOfTheNumber() {
        BinarySearch binSearch = new BinarySearch();
        assertEquals(expectedIndexForSearchKey, binSearch.runBinarySearchUsingJavaArrays(sortedArray, key));
    }
    
    @Test
    void givenASortedListOfIntegers_whenBinarySearchRunUsingCollectionsClassStaticMethodForANumber_thenGetIndexOfTheNumber() {
        BinarySearch binSearch = new BinarySearch();
        assertEquals(expectedIndexForSearchKey, binSearch.runBinarySearchUsingJavaCollections(sortedList, key));
    }
    
}
