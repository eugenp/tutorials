package com.baeldung.pattern.javahexagonalarch.service;

import com.baeldung.pattern.javahexagonalarch.domain.Employee;

import java.util.ArrayList;

public interface EmployeeService {
        ArrayList<Employee> getAllEmployees();

        void addEmployee(Employee e);

        Employee getEmployeesById(int id);
}
