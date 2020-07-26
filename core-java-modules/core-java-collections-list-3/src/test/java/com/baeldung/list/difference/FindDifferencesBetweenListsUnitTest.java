package com.baeldung.list.difference;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static final List<String> listOne = Arrays.asList("Jack", "Tom", "Sam", "John", "James", "Jack");
    private static final List<String> listTwo = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "George");

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listOne, listTwo);
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollections(listTwo, listOne);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("George", "Alan", "Daniel");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listOne, listTwo);
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaStreamsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaStream(listTwo, listOne);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Daniel", "Alan", "George");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listOne, listTwo);
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingGoogleGuavaImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingGoogleGuava(listTwo, listOne);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("George", "Alan", "Daniel");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listOne, listTwo);
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsImplAndListTwoIsFirstParameter_thenExpectedDifferencesInListTwoAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollections(listTwo, listOne);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("George", "Alan", "Daniel");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingPlainJavaListsWithDuplicatesImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneWithDuplicatesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingPlainJavaCollectionsWithDuplicates(listOne, listTwo);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Tom", "John", "Jack");
    }

    @Test
    public void givenTwoListsWithDifferentElements_whenUsingApacheCommonsCollectionsWithDuplicatesImplAndListOneIsFirstParameter_thenExpectedDifferencesInListOneWithDuplicatesAreFound() {
        List<String> differences = FindDifferencesBetweenLists.differencesUsingApacheCommonsCollectionsWithDuplicates(listOne, listTwo);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Tom", "John", "Jack");
    }

}
