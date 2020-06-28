package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindDifferenceBetweenListsUnitTest {

    private static List<String> listOneWithDuplicates = Arrays.asList("Jack", "Tom", "Sam", "James", "Tom");
    private static List<String> listTwoWithDuplicates = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "Daniel");
    private static List<String> expectedDifference = Arrays.asList("Tom", "Daniel", "Alan");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferenceBetweenLists.differenceUsingPlainJavaCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferenceBetweenLists.differenceUsingPlainJavaStream(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferenceBetweenLists.differenceUsingGoogleGuava(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImpl_thenExpectedDifferencesAreFound() {
        List<String> differences = FindDifferenceBetweenLists.differenceUsingApacheCommonsCollections(listOneWithDuplicates, listTwoWithDuplicates);
        assertExpectedDifferencesAreFound(differences);
    }

    private void assertExpectedDifferencesAreFound(List<String> differences) {
        assertAll(
                () -> assertEquals(3, differences.size()),
                () -> assertTrue(differences.containsAll(expectedDifference))
        );
    }

}
