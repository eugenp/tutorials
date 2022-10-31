package com.baeldung.collections.sorting.multiple;

import java.util.Comparator;

public class ComparatorLambda {
    public static Comparator<Person> createEmployeeComparator() {
        return Comparator.comparing(Person::getName)
          .thenComparing(Person::getAge);
    }
}
