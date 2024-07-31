package com.baeldung.avoidduplicatearraylist;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;

class AvoidDuplicateInArrayListUnitTest {

    @Test
    void givenArrayList_whenUsingSet_thenAvoidDuplicates() {
        Set<String> distinctCities = new HashSet<>(Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo"));

        String newCity = "Paris";
        distinctCities.add(newCity);
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

    @Test
    void givenArrayList_whenUsingContains_thenAvoidDuplicates() {
        List<String> distinctCities = Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo");
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        String newCity = "Madrid";
        if (!arrayListCities.contains(newCity)) {
            arrayListCities.add(newCity);
        }

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

    @Test
    void givenArrayList_whenUsingAnyMatch_thenAvoidDuplicates() {
        List<String> distinctCities = Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo");
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        String newCity = "Tamassint";
        boolean isCityPresent = arrayListCities.stream()
            .anyMatch(city -> city.equals(newCity));
        if (!isCityPresent) {
            arrayListCities.add(newCity);
        }

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

    @Test
    void givenArrayList_whenUsingFilterAndFindFirst_thenAvoidDuplicates() {
        List<String> distinctCities = Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo");
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        String newCity = "Tamassint";
        Optional<String> optionalCity = arrayListCities.stream()
            .filter(city -> city.equals(newCity))
            .findFirst();
        if (optionalCity.isEmpty()) {
            arrayListCities.add(newCity);
        }

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

    @Test
    void givenArrayList_whenUsingCollectionUtilsContainsAny_thenAvoidDuplicates() {
        List<String> distinctCities = Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo");
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        String newCity = "Tokyo";
        boolean isCityPresent = CollectionUtils.containsAny(arrayListCities, newCity);
        if (!isCityPresent) {
            arrayListCities.add(newCity);
        }

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

    @Test
    void givenArrayList_whenUsingIterablesContains_thenAvoidDuplicates() {
        List<String> distinctCities = Arrays.asList("Tamassint", "Madrid", "Paris", "Tokyo");
        ArrayList<String> arrayListCities = new ArrayList<>(distinctCities);

        String newCity = "Paris";
        boolean isCityPresent = Iterables.contains(arrayListCities, newCity);
        if (!isCityPresent) {
            arrayListCities.add(newCity);
        }

        assertThat(arrayListCities).hasSameSizeAs(distinctCities);
    }

}
