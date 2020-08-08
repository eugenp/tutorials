package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class FindDifferencesBetweenListsUnitTest {

    private static final List<String> listOne = Arrays.asList("Jack", "Tom", "Sam", "John", "James", "Jack");
    private static final List<String> listTwo = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "George");

    @Test
    public void givenListsWithTwoDifferences_whenUsingPlainJavaImpl_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>(listOne);
        differences.removeAll(listTwo);
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenListsWithThreeDifferences_whenUsingPlainJavaImpl_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>(listTwo);
        differences.removeAll(listOne);
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Daniel", "Alan", "George");
    }

    @Test
    public void givenListsWithTwoDifferences_whenUsingJavaStreams_thenDifferencesAreFound() {
        List<String> differences = listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .collect(Collectors.toList());
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenListsWithThreeDifferences_whenUsingJavaStreams_thenDifferencesAreFound() {
        List<String> differences = listTwo.stream()
                .filter(element -> !listOne.contains(element))
                .collect(Collectors.toList());
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Daniel", "Alan", "George");
    }

    @Test
    public void givenListsWithTwoDifferences_whenUsingGoogleGuava_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>(Sets.difference(Sets.newHashSet(listOne), Sets.newHashSet(listTwo)));
        assertEquals(2, differences.size());
        assertThat(differences).containsExactlyInAnyOrder("Tom", "John");
    }

    @Test
    public void givenListsWithThreeDifferences_whenUsingGoogleGuava_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>(Sets.difference(Sets.newHashSet(listTwo), Sets.newHashSet(listOne)));
        assertEquals(3, differences.size());
        assertThat(differences).containsExactlyInAnyOrder("Daniel", "Alan", "George");
    }

    @Test
    public void givenListsWithTwoDifferences_whenUsingApacheCommons_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>((CollectionUtils.removeAll(listOne, listTwo)));
        assertEquals(2, differences.size());
        assertThat(differences).containsExactly("Tom", "John");
    }

    @Test
    public void givenListsWithThreeDifferences_whenUsingApacheCommons_thenDifferencesAreFound() {
        List<String> differences = new ArrayList<>((CollectionUtils.removeAll(listTwo, listOne)));
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Daniel", "Alan", "George");
    }

    @Test
    public void givenListsWithDuplicates_whenUsingPlainJavaDuplicatesImpl_thenDifferencesWithDuplicatesAreFound() {
        List<String> differences = new ArrayList<>(listOne);
        listTwo.forEach(differences::remove);
        assertThat(differences).containsExactly("Tom", "John", "Jack");
    }

    @Test
    public void givenListsWithDuplicates_whenUsingApacheCommons_thenDifferencesWithDuplicatesAreFound() {
        List<String> differences = new ArrayList<>(CollectionUtils.subtract(listOne, listTwo));
        assertEquals(3, differences.size());
        assertThat(differences).containsExactly("Tom", "John", "Jack");
    }

}
