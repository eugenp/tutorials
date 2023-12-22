package com.baeldung.sorting;

import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingComparator;
import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingForLoop;
import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingGuava;
import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingMap;
import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingStreamAPI;
import static com.baeldung.sorting.SortingBasedOnAnotherList.sortUsingVavr;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SortingBasedOnAnotherListUnitTest {
    @Test
    public void givenTwoList_whenUsingForLoop_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingForLoop(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listWithOrder);
    }

    @Test
    public void givenTwoList_whenUsingComparator_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingComparator(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listToSort);
    }

    @Test
    public void givenTwoList_whenUsingStreamAPI_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingStreamAPI(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listToSort);
    }

    @Test
    public void givenTwoList_whenUsingMap_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingMap(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listToSort);
    }

    @Test
    public void givenTwoList_whenUsingGuavaExplicit_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingGuava(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listWithOrder);
    }

    @Test
    public void givenTwoList_whenUsingVavr_thenSort() {
        List<String> listWithOrder = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        List<String> listToSort = Arrays.asList("Pizza", "Burger", "Fries", "Coke");
        sortUsingVavr(listToSort, listWithOrder);
        List<String> expectedSortedList = Arrays.asList("Burger", "Coke", "Fries", "Pizza");
        assertEquals(expectedSortedList, listWithOrder);
    }
}