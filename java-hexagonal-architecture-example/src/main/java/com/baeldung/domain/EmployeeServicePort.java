package com.baeldung.domain;

import java.util.List;

public interface EmployeeServicePort {

    List<Employee> getAll();

    Employee get(String employeeId);

    Employee create(Employee employee);

    Employee update(Employee employee);
}
