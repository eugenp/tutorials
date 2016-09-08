package org.baeldung.java.sorting;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ComparingTest {


    @Test
    public void givenArrayObjects_whenUsingComparing_thenSortedArrayObjects() {
        List<Employee> employees = Arrays.asList(new Employee[] { new Employee("John", 23, 5000), 
          new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
          new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), 
          new Employee("Pearl", 33, 6000) });
        String sortedArrayString = "[(John,23,5000.0), (Jessica,23,4000.0), "
            + "(Steve,26,6000.0), (Frank,33,7000.0), (Pearl,33,6000.0), "
            + "(Earl,43,10000.0)]";

        employees.sort(Comparator.comparing(Employee::getAge));//.thenComparing(Employee::getName));

        assertTrue(Arrays.toString(employees.toArray()).equals(sortedArrayString));
    }

}
