package com.baeldung.java.list;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import com.baeldung.list.Country;

public class ListContainsElementFromOtherListTest {

    final private List<String> listOfLetters = Arrays.asList("a", "b", "c", "d");
    final private List<String> listOfLettersWithOverlap = Arrays.asList("d", "e", "f", "g");
    final private List<String> listOfCities = Arrays.asList("London", "Berlin", "Paris", "Brussels");

    @Test
    public void givenValuesToCompare_whenUsingCollectionsDisjoint_thenDetectElementsInTwoLists() {
        boolean shouldBeTrue = !Collections.disjoint(listOfLetters, listOfLettersWithOverlap);
        assertTrue(shouldBeTrue);

        boolean shouldBeFalse = !Collections.disjoint(listOfLetters, listOfCities);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void givenValuesToCompare_whenUsingStreams_thenDetectElementsInTwoLists() {
        boolean shouldBeTrue = listOfLetters.stream()
          .anyMatch(listOfLettersWithOverlap::contains);
        assertTrue(shouldBeTrue);

        boolean shouldBeFalse = listOfLetters.stream()
          .anyMatch(listOfCities::contains);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void givenValuesToCompare_whenUsingApacheCollectionUtils_thenDetectElementsInTwoLists() {
        boolean shouldBeTrue = CollectionUtils.containsAny(listOfLetters, listOfLettersWithOverlap);
        assertTrue(shouldBeTrue);

        boolean shouldBeFalse = CollectionUtils.containsAny(listOfLetters, listOfCities);
        assertFalse(shouldBeFalse);
    }

    @Test
    public void givenPropertiesInObjectsToCompare_whenUsingStreams_thenDetectElementsInTwoLists() {
        Country france = new Country("France", "Paris");
        Country belgium = new Country("Belgium", "Brussels");
        Country spain = new Country("Spain", "Madrid");
        List<Country> franceAndBelgium = Arrays.asList(france, belgium);
        List<Country> belgiumAndSpain = Arrays.asList(belgium, spain);

        boolean shouldBeTrue = franceAndBelgium.stream()
          .map(Country::getCapital)
          .anyMatch(belgiumAndSpain.stream()
            .map(Country::getCapital)
            .collect(toSet())::contains);

        assertTrue(shouldBeTrue);
    }

}
