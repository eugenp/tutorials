package org.baeldung.java.sorting;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class NaturalOrderExample {

    @Test
    public void sortArray() {
        int[] numbers = new int[] { 5, 1, 89, 255, 7, 88, 200, 123, 66 };

        Arrays.sort(numbers);

    }

    @Test
    public void sortEmployees() {
        Employee[] employees = new Employee[] { new Employee("John", 23, 5000), new Employee("Steve", 26, 6000), new Employee("Frank", 33, 7000),
            new Employee("Earl", 43, 10000), new Employee("Jessica", 23, 4000), new Employee("Pearl", 33, 6000) };

        Arrays.sort(employees, new Comparator<Employee>() {

            @Override
            public int compare(Employee o1, Employee o2) {
                return -(int) (o1.getSalary() - o2.getSalary());
            }
        });


    }

}
