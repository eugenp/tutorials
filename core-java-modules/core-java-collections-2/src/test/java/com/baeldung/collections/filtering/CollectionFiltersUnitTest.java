package com.baeldung.collections.filtering;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CollectionFiltersUnitTest {

    private static final Collection<Integer> BASE_INTEGER_COLLECTION = Arrays.asList(9, 14, 2, 7, 1, 5, 8);
    private static final Collection<Integer> EXPECTED_EVEN_FILTERED_COLLECTION = Arrays.asList(14, 2, 8);

    @Test
    public void givenAStringCollection_whenFilteringFourLetterWords_thenObtainTheFilteredCollection() {
        final Collection<String> baseStrings = Arrays.asList("java", "baeldung", "type", "example", "other");

        Collection<String> filtered = StreamsCollectionFilter.filterCollectionHelperMethod(baseStrings, item -> item.length() == 4);

        assertThat(filtered).containsExactlyInAnyOrder("java", "type");
    }

    @Test
    public void givenAnIntegerCollection_whenFilteringEvenValues_thenObtainTheFilteredCollectionForAllCases() {
        Collection<Integer> filteredWithStreams1 = StreamsCollectionFilter.findEvenNumbers(BASE_INTEGER_COLLECTION);
        Collection<Integer> filteredWithCollectionUtils = CollectionUtilsCollectionFilter.findEvenNumbers(new ArrayList<>(BASE_INTEGER_COLLECTION));
        Collection<Integer> filteredWithEclipseCollections = EclipseCollectionsCollectionFilter.findEvenNumbers(BASE_INTEGER_COLLECTION);
        Collection<Integer> filteredWithEclipseCollectionsUsingIterate = EclipseCollectionsCollectionFilter.findEvenNumbersUsingIterate(BASE_INTEGER_COLLECTION);
        Collection<Integer> filteredWithGuava = GuavaCollectionFilter.findEvenNumbers(BASE_INTEGER_COLLECTION);

        assertThat(filteredWithStreams1).hasSameElementsAs(filteredWithCollectionUtils)
            .hasSameElementsAs(filteredWithEclipseCollections)
            .hasSameElementsAs(filteredWithEclipseCollectionsUsingIterate)
            .hasSameElementsAs(filteredWithEclipseCollectionsUsingIterate)
            .hasSameElementsAs(filteredWithGuava)
            .hasSameElementsAs(EXPECTED_EVEN_FILTERED_COLLECTION);
    }

}
