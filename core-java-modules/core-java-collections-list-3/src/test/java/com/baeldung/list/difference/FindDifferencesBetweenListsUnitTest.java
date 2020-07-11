package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static final List<String> listOneWithDuplicates = Arrays.asList("Jack", "Tom", "Sam", "James", "Tom");
    private static final List<String> listTwoWithDuplicates = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "Daniel");
    private static final List<String> expectedDifferences = Arrays.asList("Tom", "Daniel", "Alan");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(3, differences.size());
        assertTrue(differences.containsAll(expectedDifferences));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(3, differences.size());
        assertTrue(differences.containsAll(expectedDifferences));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(3, differences.size());
        assertTrue(differences.containsAll(expectedDifferences));
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertEquals(3, differences.size());
        assertTrue(differences.containsAll(expectedDifferences));
    }

}
