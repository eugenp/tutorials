package com.baeldung.findItems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test; 

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FindItemsBasedOnOtherStreamUnitTest {

    private List<Employee> employeeList = new ArrayList<Employee>();

    private List<Department> departmentList = new ArrayList<Department>();

    @Test
    public void givenDepartmentList_thenEmployeeListIsFilteredCorrectly() {
        Integer expectedId = 1002;

        populate(employeeList, departmentList);

        List<Employee> filteredList = employeeList.stream()
          .filter(empl -> departmentList.stream()
          .anyMatch(dept -> dept.getDepartment()
          .equals("sales") && empl.getEmployeeId()
          .equals(dept.getEmployeeId())))
          .collect(Collectors.toList());

        assertEquals(expectedId, filteredList.get(0).getEmployeeId());
    }

    @Test
    public void givenEmployeeListToExclude_thenDepartmentListIsFilteredCorrectly() {
        String expectedDepartment = "sales";

        List<Integer> employeeIdList = Arrays.asList(1001,1002,1004,1005);

        populate(employeeList, departmentList);
      
        List<Department> filteredList = departmentList.stream()
          .filter(dept -> !employeeIdList.contains(dept.getEmployeeId()))
          .collect(Collectors.toList());
        
        assertNotEquals(expectedDepartment, filteredList.get(0).getDepartment());
    }

    private void populate(List<Employee> EmplList, List<Department> deptList) {
        Employee employee1 = new Employee(1001, "empl1");
        Employee employee2 = new Employee(1002, "empl2");
        Employee employee3 = new Employee(1003, "empl3");

        Collections.addAll(EmplList, employee1, employee2, employee3);

        Department department1 = new Department(1002, "sales");
        Department department2 = new Department(1003, "marketing");
        Department department3 = new Department(1004, "sales");

        Collections.addAll(deptList, department1, department2, department3);
    }
}

class Employee {
    private Integer employeeId;
    private String employeeName;

    Employee(Integer employeeId, String employeeName) {
        super();
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    Integer getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

}

class Department {
    private Integer employeeId;
    private String department;

    Department(Integer employeeId, String department) {
        super();
        this.employeeId = employeeId;
        this.department = department;
    }

    Integer getEmployeeId() {
        return employeeId;
    }

    String getDepartment() {
        return department;
    }

}
