package com.baeldung.java.filtering;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

public class CollectionUtilsCollectionFilter {

    static public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> apacheEventNumberPredicate = new Predicate<Integer>() {

            @Override
            public boolean evaluate(Integer object) {
                return object % 2 == 0;
            }
        };

        CollectionUtils.filter(baseCollection, apacheEventNumberPredicate);
        return baseCollection;
    }
}
