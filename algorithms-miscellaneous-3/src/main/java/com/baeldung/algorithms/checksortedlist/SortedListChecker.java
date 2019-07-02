package com.baeldung.algorithms.checksortedlist;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;;

public class SortedListChecker {

    private SortedListChecker() {
        throw new AssertionError();
    }

    public static boolean checkIfSortedUsingIterativeApproach(List<String> listOfStrings) {
        if (isEmpty(listOfStrings) || listOfStrings.size() == 1) {
            return true;
        }

        Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static boolean checkIfSortedUsingIterativeApproach(List<Employee> employees, Comparator<Employee> employeeComparator) {
        if (isEmpty(employees) || employees.size() == 1) {
            return true;
        }

        Iterator<Employee> iter = employees.iterator();
        Employee current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (employeeComparator.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static boolean checkIfSortedUsingRecursion(List<String> listOfStrings) {
        return isSortedRecursive(listOfStrings, listOfStrings.size());
    }

    public static boolean isSortedRecursive(List<String> listOfStrings, int index) {
        if (index < 2) {
            return true;
        } else if (listOfStrings.get(index - 2)
            .compareTo(listOfStrings.get(index - 1)) > 0) {
            return false;
        } else {
            return isSortedRecursive(listOfStrings, index - 1);
        }
    }

    public static boolean checkIfSortedUsingOrderingClass(List<String> listOfStrings) {
        return Ordering.<String> natural()
            .isOrdered(listOfStrings);
    }

    public static boolean checkIfSortedUsingOrderingClass(List<Employee> employees, Comparator<Employee> employeeComparator) {
        return Ordering.from(employeeComparator)
            .isOrdered(employees);
    }

    public static boolean checkIfSortedUsingOrderingClassHandlingNull(List<String> listOfStrings) {
        return Ordering.<String> natural()
            .nullsLast()
            .isOrdered(listOfStrings);
    }

    public static boolean checkIfSortedUsingComparators(List<String> listOfStrings) {
        return Comparators.isInOrder(listOfStrings, Comparator.<String> naturalOrder());
    }

}
