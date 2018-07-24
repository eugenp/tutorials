package com.baeldung.java.filtering;

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

    static public Collection<Integer> findEvenNumbersUsingLambda(Collection<Integer> baseCollection) {
        return baseCollection.stream()
            .filter(item -> item % 2 == 0)
            .collect(Collectors.toList());
    }

    static public Collection<Integer> findEvenNumbersUsingPredicate(Collection<Integer> baseCollection) {
        Predicate<Integer> evenNumberPredicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i % 2 == 0;
            }
        };

        return baseCollection.stream()
            .filter(evenNumberPredicate)
            .collect(Collectors.toList());
    }
}
