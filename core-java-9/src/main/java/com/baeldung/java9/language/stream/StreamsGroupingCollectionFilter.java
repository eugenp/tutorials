package com.baeldung.java9.language.stream;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StreamsGroupingCollectionFilter {

    static public Map<Integer, List<Integer>> findEvenNumbersAfterGroupingByQuantityOfDigits(Collection<Integer> baseCollection) {
        Function<Integer, Integer> getQuantityOfDigits = item -> (int) Math.log10(item) + 1;

        return baseCollection.stream()
            .collect(groupingBy(getQuantityOfDigits, filtering(item -> item % 2 == 0, toList())));
    }

    static public Map<Integer, List<Integer>> findEvenNumbersBeforeGroupingByQuantityOfDigits(Collection<Integer> baseCollection) {

        return baseCollection.stream()
            .filter(item -> item % 2 == 0)
            .collect(groupingBy(item -> (int) Math.log10(item) + 1, toList()));
    }
}
