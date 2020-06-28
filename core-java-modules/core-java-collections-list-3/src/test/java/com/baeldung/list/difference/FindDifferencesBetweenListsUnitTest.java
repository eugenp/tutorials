package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static List<String> listOneWithDuplicates = Arrays.asList("Jack", "Tom", "Sam", "James", "Tom");
    private static List<String> listTwoWithDuplicates = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "Daniel");
    private static List<String> expectedDifferences = Arrays.asList("Tom", "Daniel", "Alan");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    private void assertExpectedDifferencesAreFound(List<String> differences) {
        assertAll(
                () -> assertEquals(3, differences.size()),
                () -> assertTrue(differences.containsAll(expectedDifferences))
        );
    }

}
