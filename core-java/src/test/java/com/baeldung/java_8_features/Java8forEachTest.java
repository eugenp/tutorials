package com.baeldung.java_8_features;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.Test;

public class Java8forEachTest {

    private List<Employee> getEmployees() {

        Employee mark = new Employee(100000, "Mark Zuckerberg");
        Employee steve = new Employee(200000, "Steve Jobs");

        List<Employee> employees = new ArrayList<>();
        employees.add(mark);
        employees.add(steve);

        return employees;
    }

    private List<Employee> getOtherEmployees() {

        Employee larry = new Employee(500000, "Larry Page");
        Employee sergey = new Employee(800000, "Sergey Brin");

        List<Employee> employees = new ArrayList<>();
        employees.add(larry);
        employees.add(sergey);

        return employees;
    }

    @Test
    public void checkNewSalaryIncrement_whenUsingForLoop() {

        List<Employee> employees = getEmployees();
        for (Employee e : employees) {
            e.setSalary(e.getSalary() * 1.10);
        }

        assertEquals(employees.get(0).getSalary(), 110000, 1);
        assertEquals(employees.get(1).getSalary(), 220000, 1);
    }

    @Test
    public void checkNewSalaryIncrement_whenUsingforEach() {

        List<Employee> employees = getEmployees();
        employees.stream().forEach(e -> e.setSalary(e.getSalary() * 1.10));

        assertEquals(employees.get(0).getSalary(), 110000, 1);
        assertEquals(employees.get(1).getSalary(), 220000, 1);

    }

    @Test
    public void performIllegalOperation_whenUsingForEachInSameStream() {

        List<Employee> employees = getEmployees();
        Stream<Employee> employeesStream = employees.stream();
        employeesStream.forEach(e -> e.setSalary(e.getSalary() * 1.10));
        employeesStream.forEach(e -> e.setSalary(e.getSalary() * 1.20));

    }

    @Test
    public void performValidOperation_whenPerformingForEachInDifferentStreams() {

        List<Employee> employees = getEmployees();
        employees.stream().forEach(e -> e.setSalary(e.getSalary() * 1.10));

        assertEquals(employees.get(0).getSalary(), 110000, 1);
        assertEquals(employees.get(1).getSalary(), 220000, 1);

        employees.stream().forEach(e -> e.setSalary(e.getSalary() * 1.20));

        assertEquals(employees.get(0).getSalary(), 132000, 1);
        assertEquals(employees.get(1).getSalary(), 264000, 1);

    }

    @Test
    public void reuseLambdaOperation_whenApplyingItToDifferentStreams_thenVerifyOperatiosAreCorrect() {

        Consumer<Employee> giveRaise = e -> {
            e.setSalary(e.getSalary() * 1.10);
        };
        
        List<Employee> employees = getEmployees();
        List<Employee> otherEmployees = getOtherEmployees();
        
        employees.stream().forEach(giveRaise);
        otherEmployees.stream().forEach(giveRaise);
        
        assertEquals(employees.get(0).getSalary(), 110000, 1);
        assertEquals(employees.get(1).getSalary(), 220000, 1);
        
        assertEquals(otherEmployees.get(0).getSalary(), 550000, 1);
        assertEquals(otherEmployees.get(1).getSalary(), 880000, 1);

    }
}
