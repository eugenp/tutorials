package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Employee;

public interface EmployeeRepositoryPort {

    void create(String name, String role, long salary);

    Employee getEmployee(Integer userId);
}