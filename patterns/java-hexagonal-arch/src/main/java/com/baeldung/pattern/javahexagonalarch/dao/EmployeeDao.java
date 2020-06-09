package com.baeldung.pattern.javahexagonalarch.dao;

import com.baeldung.pattern.javahexagonalarch.domain.Employee;

import java.util.ArrayList;

public interface EmployeeDao {
        ArrayList<Employee> getAllEmployees();

        void addEmployee(Employee e);

        Employee getEmployeesById(int id);
}
