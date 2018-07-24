package com.baeldung.java.filtering;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class GuavaCollectionFilter {

    static public Collection<Integer> findEvenNumbers(Collection<Integer> baseCollection) {
        Predicate<Integer> guavaPredicate = new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        };
        Collection<Integer> filteredCollection = Collections2.filter(baseCollection, guavaPredicate);
        return filteredCollection;
    }

}
