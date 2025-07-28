package com.baeldung.sortlistofpair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

public class SortListOfPairUnitTest {

    private List<Pair<String, Integer>> getUnsortedInput() {
        return Arrays.asList(
            //@formatter:off
            Pair.of("False", 5),
            Pair.of("Yes", 3),
            Pair.of("True", 4),
            Pair.of("No", 2),
            Pair.of("X", 1)
            //@formatter:on
        );
    }

    private static final List<Pair<String, Integer>> EXPECTED = List.of(
        //@formatter:off
        Pair.of("X", 1),
        Pair.of("No", 2),
        Pair.of("Yes", 3),
        Pair.of("True", 4),
        Pair.of("False", 5)
        //@formatter:on
    );

    @Test
    void whenSortingUsingAnonymousComparator_thenCorrect() {
        List<Pair<String, Integer>> myList = getUnsortedInput();
        myList.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getRight()
                    .compareTo(o2.getRight());
            }
        });

        assertEquals(EXPECTED, myList);
    }

    @Test
    void whenSortingUsingLamda_thenCorrect() {
        List<Pair<String, Integer>> myList = getUnsortedInput();
        myList.sort((p1, p2) -> p1.getRight()
            .compareTo(p2.getRight()));

        assertEquals(EXPECTED, myList);
    }

    @Test
    void whenSortingUsingComparatorComparing_thenCorrect() {
        List<Pair<String, Integer>> myList = getUnsortedInput();
        myList.sort(Comparator.comparing(Pair::getRight));

        assertEquals(EXPECTED, myList);
    }

    @Test
    void whenInPlaceSortingImmutableList_thenThrowExpectedException() {
        List<Pair<String, Integer>> immutableUnsortedList = List.copyOf(getUnsortedInput());
        assertThrows(UnsupportedOperationException.class, () -> immutableUnsortedList.sort(Comparator.comparing(Pair::getRight)));
    }

    @Test
    void whenSortingUsingStream_thenCorrect() {
        List<Pair<String, Integer>> immutableUnsortedList = List.copyOf(getUnsortedInput());

        List<Pair<String, Integer>> sorted = immutableUnsortedList.stream()
            .sorted(Comparator.comparing(Pair::getRight))
            .toList();

        assertEquals(EXPECTED, sorted);
    }
}