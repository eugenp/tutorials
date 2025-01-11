package com.baeldung.mapstruct.iterable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.baeldung.mapstruct.iterable.model.Department;
import com.baeldung.mapstruct.iterable.model.Employee;

public class DepartmentMapperTest {

    private final DepartmentMapper departmentMapper = DepartmentMapper.INSTANCE;
    private final List<Employee> employees = Arrays.asList(
        new Employee("John", "(555)1111-1234"),
        new Employee("Jane", "(555)2222-4321")
    );

    @Test
    void givenListOfEmployees_whenMapDepartment_thenAssertMappingIsSuccessful() {
        Department department = departmentMapper.map(employees);

        assertNotNull(department);
        assertNotNull(department.getEmployees());
        assertEquals(employees.size(), department.getEmployees().size());
        assertEquals(employees.get(0).getName(), department.getEmployees().get(0).getName());
        assertEquals(employees.get(0).getPhoneNumber(), department.getEmployees().get(0).getPhoneNumber());
        assertEquals(employees.get(1).getName(), department.getEmployees().get(1).getName());
        assertEquals(employees.get(1).getPhoneNumber(), department.getEmployees().get(1).getPhoneNumber());
    }

    @Test
    void givenListOfEmployeesAndManager_whenMapDepartment_thenAssertMappingIsSuccessful() {
        Department department = departmentMapper.mapWithManager(employees, employees.get(0));

        assertNotNull(department);
        assertNotNull(department.getEmployees());
        assertEquals(employees.size(), department.getEmployees().size());
        assertEquals(employees.get(0).getName(), department.getEmployees().get(0).getName());
        assertEquals(employees.get(0).getPhoneNumber(), department.getEmployees().get(0).getPhoneNumber());
        assertEquals(employees.get(1).getName(), department.getEmployees().get(1).getName());
        assertEquals(employees.get(1).getPhoneNumber(), department.getEmployees().get(1).getPhoneNumber());

        assertNotNull(department.getManager());
        assertEquals(department.getManager().getName(), department.getEmployees().get(0).getName());
        assertEquals(department.getManager().getPhoneNumber(), department.getEmployees().get(0).getPhoneNumber());
    }
}