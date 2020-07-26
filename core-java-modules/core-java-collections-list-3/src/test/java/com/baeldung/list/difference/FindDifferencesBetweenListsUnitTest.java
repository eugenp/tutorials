package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static final List<String> listOne = Arrays.asList("Jack", "Tom", "Sam", "John", "James", "Jack");
    private static final List<String> listTwo = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "George");
    private static final List<String> expectedDifferencesInListOneWithoutDuplicates = Arrays.asList("Tom", "John");
    private static final List<String> expectedDifferencesInListOneWithDuplicates = Arrays.asList("Tom", "John", "Jack");
    private static final List<String> expectedDifferencesInListTwoWithoutDuplicates = Arrays.asList("Daniel", "Alan", "George");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listTwo, listOne);
        assertEquals(expectedDifferencesInListTwoWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwoWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listTwo, listOne);
        assertEquals(expectedDifferencesInListTwoWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwoWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listTwo, listOne);
        assertEquals(expectedDifferencesInListTwoWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwoWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listTwo, listOne);
        assertEquals(expectedDifferencesInListTwoWithoutDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwoWithoutDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsWithDuplicatesImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneWithDuplicatesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollectionsWithDuplicates(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithDuplicates));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsWithDuplicatesImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneWithDuplicatesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollectionsWithDuplicates(listOne, listTwo);
        assertEquals(expectedDifferencesInListOneWithDuplicates.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOneWithDuplicates));
    }

}
