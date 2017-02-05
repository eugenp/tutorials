package com.baeldung.server.repository;

import java.util.List;

import com.baeldung.server.model.Employee;

public interface EmployeeRepository {

    public List<Employee> getAllEmployees();

    public Employee getEmployee(int id);

    public void updateEmployee(Employee employee, int id);

    public void deleteEmployee(int id);

    public void addEmployee(Employee employee);
}
