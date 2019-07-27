package com.baeldung.algorithms.stringsortingbynumber;

import com.baeldung.algorithms.stringsortingbynumber.NaturalOrderComparators;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NaturalOrderComparatorsUnitTest {

    @Test
    public void givenSimpleStringsContainingIntsAndDoubles_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.4", "d2.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("a1", "d2.2", "d2.3d", "d2.4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    public void givenSimpleStringsContainingIntsAndDoublesWithAnInvalidNumber_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.4", "d2.3.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("d2.3.3d", "a1", "d2.2", "d2.4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    public void givenAllForseenProblems_whenSortedByRegex_checkSortingCorrect() {

        List<String> testStrings = Arrays.asList("a1", "b3", "c4", "d2.2", "d2.f4", "d2.3.3d");

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        List<String> expected = Arrays.asList("d2.3.3d", "a1", "d2.2", "d2.f4", "b3", "c4");

        assertEquals(expected, testStrings);


    }

    @Test
    public void givenComplexStringsContainingSeparatedNumbers_whenSortedByRegex_checkNumbersCondensedAndSorted() {

        List<String> testStrings = Arrays.asList("a1b2c5", "b3ght3.2", "something65.thensomething5"); //125, 33.2, 65.5

        List<String> expected = Arrays.asList("b3ght3.2", "something65.thensomething5", "a1b2c5" );

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        assertEquals(expected, testStrings);

    }

    @Test
    public void givenStringsNotContainingNumbers_whenSortedByRegex_checkOrderNotChanged() {

        List<String> testStrings = Arrays.asList("a", "c", "d", "e");
        List<String> expected = new ArrayList<>(testStrings);

        testStrings.sort(NaturalOrderComparators.createNaturalOrderRegexComparator());

        assertEquals(expected, testStrings);
    }
}