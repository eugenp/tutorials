package com.baeldung.jaxws.introduction.server.repository;


import com.baeldung.jaxws.introduction.server.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    Employee updateEmployee(Employee update, int id);

    boolean deleteEmployee(int id);

    Employee addEmployee(Employee employee);
}
