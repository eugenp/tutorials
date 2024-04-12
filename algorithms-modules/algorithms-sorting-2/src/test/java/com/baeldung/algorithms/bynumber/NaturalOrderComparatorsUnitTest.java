package com.baeldung.algorithms.bynumber;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class NaturalOrderComparatorsUnitTest {

    @Test
    void givenSimpleStringsContainingIntsAndDoubles_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.4", "d2.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("a1", "d2.2", "d2.3d", "d2.4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    void givenSimpleStringsContainingIntsAndDoublesWithAnInvalidNumber_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.4", "d2.3.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("d2.3.3d", "a1", "d2.2", "d2.4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    void givenAllForseenProblems_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.f4", "d2.3.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("d2.3.3d", "a1", "d2.2", "d2.f4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    void givenComplexStringsContainingSeparatedNumbers_whenSortedByRegex_checkNumbersCondensedAndSorted() {

        List<String> testStrings = Arrays.asList("a1b2c5", "b3ght3.2", "something65.thensomething5"); //125, 33.2, 65.5

        List<String> expected = Arrays.asList("b3ght3.2", "something65.thensomething5", "a1b2c5" );

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        assertEquals(expected, testStrings);

    }

    @Test
    void givenStringsNotContainingNumbers_whenSortedByRegex_checkOrderNotChanged() {

        List<String> testStrings = Arrays.asList("a", "c", "d", "e");
        List<String> expected = new ArrayList<>(testStrings);

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        assertEquals(expected, testStrings);
    }
}