package com.baeldung.java8.comparator;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import lombok.Data;
import static org.junit.Assert.assertTrue;

public class Java8ComparatorTest {

    private Employee[] employees;
    private Employee[] employeesArrayWithNulls;
    private Employee[] sortedEmployeesByName;
    private Employee[] sortedEmployeesByNameDesc;
    private Employee[] sortedEmployeesByAge;
    private Employee[] sortedEmployeesByMobile;
    private Employee[] sortedEmployeesBySalary;
    private Employee[] sortedEmployeesArray_WithNullsFirst;
    private Employee[] sortedEmployeesArray_WithNullsLast;
    private Employee[] sortedEmployeesByNameAge;
    private Employee[] someMoreEmployees;
    private Employee[] sortedEmployeesByAgeName;;

    @Before
    public void initData() {
        employees = new Employee[] { new Employee("John", 25, 3000, 9922001), new Employee("Ace", 22, 2000, 5924001),
             new Employee("Keith", 35, 4000, 3924401) };
        employeesArrayWithNulls = new Employee[] { new Employee("John", 25, 3000, 9922001), null, new Employee("Ace", 22, 2000, 5924001),
             null, new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByName = new Employee[] { new Employee("Ace", 22, 2000, 5924001), 
            new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesByNameDesc = new Employee[] { new Employee("Keith", 35, 4000, 3924401), new Employee("John", 25, 3000, 9922001),
             new Employee("Ace", 22, 2000, 5924001) };

        sortedEmployeesByAge = new Employee[] {  new Employee("Ace", 22, 2000, 5924001),
            new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByMobile = new Employee[] { new Employee("Keith", 35, 4000, 3924401), new Employee("Ace", 22, 2000, 5924001),
             new Employee("John", 25, 3000, 9922001), };

        sortedEmployeesBySalary = new Employee[] { new Employee("Ace", 22, 2000, 5924001), new Employee("John", 25, 3000, 9922001),
            new Employee("Keith", 35, 4000, 3924401),  };

        sortedEmployeesArray_WithNullsFirst = new Employee[] { null, null, new Employee("Ace", 22, 2000, 5924001),
             new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesArray_WithNullsLast = new Employee[] { new Employee("Ace", 22, 2000, 5924001), 
            new Employee("John", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401), null, null };

        someMoreEmployees = new Employee[] { new Employee("Jake", 25, 3000, 9922001), new Employee("Jake", 22, 2000, 5924001),
            new Employee("Ace", 22, 3000, 6423001),  new Employee("Keith", 35, 4000, 3924401) };

        sortedEmployeesByAgeName = new Employee[] {  new Employee("Ace", 22, 3000, 6423001),
            new Employee("Jake", 22, 2000, 5924001), new Employee("Jake", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
        sortedEmployeesByNameAge = new Employee[] { new Employee("Ace", 22, 3000, 6423001), 
            new Employee("Jake", 22, 2000, 5924001), new Employee("Jake", 25, 3000, 9922001), new Employee("Keith", 35, 4000, 3924401) };
    }

    @Test
    public void givenEmployeeArray_whenUsingComparing_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Arrays.sort(employees, employeeNameComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByName));
    }

    @Test
    public void givenEmployeeArray_whenUsingComparingWithComparator_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName, (s1, s2) -> {
            return s2.compareTo(s1);
        });
        Arrays.sort(employees, employeeNameComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByNameDesc));
    }

    @Test
    public void givenEmployeeArray_whenUsingComparingInt_thenCheckingSort() {
        Comparator<Employee> employeeAgeComparator = Comparator.comparingInt(Employee::getAge);
        Arrays.sort(employees, employeeAgeComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByAge));
    }

    @Test
    public void givenEmployeeArray_whenUsingComparingLong_thenCheckingSort() {
        Comparator<Employee> employeeMobileComparator = Comparator.comparingLong(Employee::getMobile);
        Arrays.sort(employees, employeeMobileComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByMobile));
    }

    @Test
    public void givenEmployeeArray_whenUsingComparingDouble_thenCheckingSort() {
        Comparator<Employee> employeeSalaryComparator = Comparator.comparingDouble(Employee::getSalary);
        Arrays.sort(employees, employeeSalaryComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesBySalary));
    }

    @Test
    public void givenEmployeeArray_whenUsingNaturalOrder_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> naturalOrder();
        Arrays.sort(employees, employeeNameComparator);
//         System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByName));
    }

    @Test
    public void givenEmployeeArray_whenUsingReverseOrder_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.<Employee> reverseOrder();
        Arrays.sort(employees, employeeNameComparator);
//        System.out.println(Arrays.toString(employees));
        assertTrue(Arrays.equals(employees, sortedEmployeesByNameDesc));
    }

    @Test
    public void givenEmployeeArray_whenUsingNullFirst_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullFirst = Comparator.nullsFirst(employeeNameComparator);
        Arrays.sort(employeesArrayWithNulls, employeeNameComparator_nullFirst);
//         System.out.println(Arrays.toString(employeesArrayWithNulls));
        assertTrue(Arrays.equals(employeesArrayWithNulls, sortedEmployeesArray_WithNullsFirst));
    }

    @Test
    public void givenEmployeeArray_whenUsingNullLast_thenCheckingSort() {
        Comparator<Employee> employeeNameComparator = Comparator.comparing(Employee::getName);
        Comparator<Employee> employeeNameComparator_nullLast = Comparator.nullsLast(employeeNameComparator);
        Arrays.sort(employeesArrayWithNulls, employeeNameComparator_nullLast);
//         System.out.println(Arrays.toString(employeesArrayWithNulls));
        assertTrue(Arrays.equals(employeesArrayWithNulls, sortedEmployeesArray_WithNullsLast));
    }

    @Test
    public void givenEmployeeArray_whenUsingThenComparing_thenCheckingSort() {
        Comparator<Employee> employee_Age_Name_Comparator = Comparator.comparing(Employee::getAge).thenComparing(Employee::getName);

        Arrays.sort(someMoreEmployees, employee_Age_Name_Comparator);
//         System.out.println(Arrays.toString(someMoreEmployees));
        assertTrue(Arrays.equals(someMoreEmployees, sortedEmployeesByAgeName));
    }

    @Test
    public void givenEmployeeArray_whenUsingThenComparingInt_thenCheckingSort() {
        Comparator<Employee> employee_Name_Age_Comparator = Comparator.comparing(Employee::getName).thenComparingInt(Employee::getAge);

        Arrays.sort(someMoreEmployees, employee_Name_Age_Comparator);
//        System.out.println(Arrays.toString(someMoreEmployees));
        assertTrue(Arrays.equals(someMoreEmployees, sortedEmployeesByNameAge));
    }

     @Before
     public void printData() {
//     System.out.println("employees");
//     System.out.println(Arrays.toString(employees));
    //
//     System.out.println("employeesArrayWithNulls");
//     System.out.println(Arrays.toString(employeesArrayWithNulls));
    //
    // System.out.println("sortedEmployeesByName");
    // System.out.println(Arrays.toString(sortedEmployeesByName));
    //
    // System.out.println("sortedEmployeesByNameDesc");
    // System.out.println(Arrays.toString(sortedEmployeesByNameDesc));
    //
    // System.out.println("sortedEmployeesByAge");
    // System.out.println(Arrays.toString(sortedEmployeesByAge));
    //
    // System.out.println("sortedEmployeesByMobile");
    // System.out.println(Arrays.toString(sortedEmployeesByMobile));
    //
    // System.out.println("sortedEmployeesBySalary");
    // System.out.println(Arrays.toString(sortedEmployeesBySalary));
    //
    // System.out.println("sortedEmployeesArray_WithNullsFirst");
    // System.out.println(Arrays.toString(sortedEmployeesArray_WithNullsFirst));
    //
    // System.out.println("sortedEmployeesArray_WithNullsLast");
    // System.out.println(Arrays.toString(sortedEmployeesArray_WithNullsLast));
    //
    // System.out.println("sortedEmployeesByNameAge");
    // System.out.println(Arrays.toString(sortedEmployeesByNameAge));
    //
//     System.out.println("someMoreEmployees");
//     System.out.println(Arrays.toString(someMoreEmployees));
    //
     }
}
