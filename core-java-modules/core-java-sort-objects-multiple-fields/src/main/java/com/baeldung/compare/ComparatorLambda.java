package com.baeldung.compare;

import java.util.Comparator;

public class ComparatorLambda {
    public static Comparator<Employee> createEmployeeComparator() {
        return Comparator.comparing(Employee::getName)
          .thenComparing(Employee::getAge);
    }
}
