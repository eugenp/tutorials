package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static final List<String> listOneWithDuplicates = Arrays.asList("Jack", "Tom", "Sam", "John", "James", "Tom");
    private static final List<String> listTwoWithDuplicates = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "Daniel", "George");
    private static final List<String> expectedDifferencesInListOne = Arrays.asList("Tom", "John");
    private static final List<String> expectedDifferencesInListTwo = Arrays.asList("Daniel", "Alan", "George");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(expectedDifferencesInListOne.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOne));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listTwoWithDuplicates, listOneWithDuplicates);
        assertEquals(expectedDifferencesInListTwo.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwo));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(expectedDifferencesInListOne.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOne));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listTwoWithDuplicates, listOneWithDuplicates);
        assertEquals(expectedDifferencesInListTwo.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwo));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(expectedDifferencesInListOne.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOne));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listTwoWithDuplicates, listOneWithDuplicates);
        assertEquals(expectedDifferencesInListTwo.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwo));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(expectedDifferencesInListOne.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListOne));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listTwoWithDuplicates, listOneWithDuplicates);
        assertEquals(expectedDifferencesInListTwo.size(), differences.size());
        assertTrue(differences.containsAll(expectedDifferencesInListTwo));
    }

}
