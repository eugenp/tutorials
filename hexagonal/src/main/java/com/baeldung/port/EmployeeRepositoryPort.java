package com.baeldung.port;

import com.baeldung.domain.Employee;

public interface EmployeeRepositoryPort {

    void create(String name, String role, long salary);

    Employee getEmployee(Integer userId);
}