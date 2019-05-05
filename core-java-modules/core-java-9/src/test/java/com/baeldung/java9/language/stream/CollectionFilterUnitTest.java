package com.baeldung.java9.language.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CollectionFilterUnitTest {

    private static final Collection<Integer> BASE_INTEGER_COLLECTION = Arrays.asList(9, 12, 55, 56, 101, 115, 8002, 223, 2668, 19, 8);
    private static final Map<Integer, List<Integer>> EXPECTED_EVEN_FILTERED_AFTER_GROUPING_MAP = createExpectedFilterAfterGroupingMap();
    private static Map<Integer, List<Integer>> createExpectedFilterAfterGroupingMap() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(1, Arrays.asList(8));
        map.put(2, Arrays.asList(12, 56));
        map.put(3, Collections.emptyList());
        map.put(4, Arrays.asList(8002, 2668));
        return map;

    }

    private static final Map<Integer, List<Integer>> EXPECTED_EVEN_FILTERED_BEFORE_GROUPING_MAP = createExpectedFilterBeforeGroupingMap();
    private static Map<Integer, List<Integer>> createExpectedFilterBeforeGroupingMap() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(1, Arrays.asList(8));
        map.put(2, Arrays.asList(12, 56));
        map.put(4, Arrays.asList(8002, 2668));
        return map;

    }

    @Test
    public void givenAStringCollection_whenFilteringFourLetterWords_thenObtainTheFilteredCollection() {
        Map<Integer, List<Integer>> filteredAfterGroupingMap = StreamsGroupingCollectionFilter.findEvenNumbersAfterGroupingByQuantityOfDigits(BASE_INTEGER_COLLECTION);
        Map<Integer, List<Integer>> filteredBeforeGroupingMap = StreamsGroupingCollectionFilter.findEvenNumbersBeforeGroupingByQuantityOfDigits(BASE_INTEGER_COLLECTION);

        assertThat(filteredAfterGroupingMap).containsAllEntriesOf(EXPECTED_EVEN_FILTERED_AFTER_GROUPING_MAP);
        assertThat(filteredBeforeGroupingMap).doesNotContainKey(3)
            .containsAllEntriesOf(EXPECTED_EVEN_FILTERED_BEFORE_GROUPING_MAP);
    }

}
