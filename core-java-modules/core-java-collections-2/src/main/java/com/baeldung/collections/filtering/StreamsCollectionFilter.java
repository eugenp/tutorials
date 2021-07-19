package com.baeldung.collections.filtering;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamsCollectionFilter {

    public static <T> Collection<T> filterCollectionHelperMethod(Collection<T> baseCollection, Predicate<T> predicate) {
        return baseCollection.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    static public Collection<Integer> findEvenNumbersUsingHelperMethod(Collection<Integer> baseCollection) {
        return filterCollectionHelperMethod(baseCollection, item -> item % 2 == 0);
    }

    static public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> streamsPredicate = item -> item % 2 == 0;

        return baseCollection.stream()
            .filter(streamsPredicate)
            .collect(Collectors.toList());
    }
}
