package com.baeldung.array;

import com.baeldung.sort.Employee;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class SortedArrayCheckerUnitTest {
    private static final int[] INTEGER_SORTED = {1, 3, 5, 7, 9};
    private static final int[] INTEGER_NOT_SORTED = {1, 3, 11, 7};

    private static final String[] STRING_SORTED = {"abc", "cde", "fgh"};
    private static final String[] STRING_NOT_SORTED = {"abc", "fgh", "cde", "ijk"};

    private static final Employee[] EMPLOYEES_SORTED_BY_NAME = {
            new Employee(1, "Carlos", 26),
            new Employee(2, "Daniel", 31),
            new Employee(3, "Marta", 27)};

    private static final Employee[] EMPLOYEES_NOT_SORTED_BY_NAME = {
            new Employee(1, "Daniel", 31),
            new Employee(2, "Carlos", 26),
            new Employee(3, "Marta", 27)};

    private static final Employee[] EMPLOYEES_SORTED_BY_AGE = {
            new Employee(1, "Carlos", 26),
            new Employee(2, "Marta", 27),
            new Employee(3, "Daniel", 31)};

    private static final Employee[] EMPLOYEES_NOT_SORTED_BY_AGE = {
            new Employee(1, "Marta", 27),
            new Employee(2, "Carlos", 26),
            new Employee(3, "Daniel", 31)};

    private SortedArrayChecker sortedArrayChecker;

    @Before
    public void setup() {
        sortedArrayChecker = new SortedArrayChecker();
    }

    @Test
    public void givenIntegerArray_thenReturnIfItIsSortedOrNot() {
        assertThat(sortedArrayChecker.isSorted(INTEGER_SORTED)).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(INTEGER_NOT_SORTED)).isEqualTo(false);

        assertThat(sortedArrayChecker.isSorted(INTEGER_SORTED, INTEGER_SORTED.length)).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(INTEGER_NOT_SORTED, INTEGER_NOT_SORTED.length)).isEqualTo(false);
    }

    @Test
    public void givenStringArray_thenReturnIfItIsSortedOrNot() {
        assertThat(sortedArrayChecker.isSorted(STRING_SORTED)).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(STRING_NOT_SORTED)).isEqualTo(false);

        assertThat(sortedArrayChecker.isSorted(STRING_SORTED, STRING_SORTED.length)).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(STRING_NOT_SORTED, STRING_NOT_SORTED.length)).isEqualTo(false);
    }

    @Test
    public void givenEmployeeArray_thenReturnIfItIsSortedOrNot() {
        assertThat(sortedArrayChecker.isSorted(EMPLOYEES_SORTED_BY_NAME, Comparator.comparing(Employee::getName))).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(EMPLOYEES_NOT_SORTED_BY_NAME, Comparator.comparing(Employee::getName))).isEqualTo(false);

        assertThat(sortedArrayChecker.isSorted(EMPLOYEES_SORTED_BY_AGE, Comparator.comparingInt(Employee::getAge))).isEqualTo(true);
        assertThat(sortedArrayChecker.isSorted(EMPLOYEES_NOT_SORTED_BY_AGE, Comparator.comparingInt(Employee::getAge))).isEqualTo(false);

        assertThat(sortedArrayChecker
                .isSorted(EMPLOYEES_SORTED_BY_AGE, Comparator.comparingInt(Employee::getAge), EMPLOYEES_SORTED_BY_AGE.length))
                .isEqualTo(true);
        assertThat(sortedArrayChecker
                .isSorted(EMPLOYEES_NOT_SORTED_BY_AGE, Comparator.comparingInt(Employee::getAge), EMPLOYEES_NOT_SORTED_BY_AGE.length))
                .isEqualTo(false);
    }

}