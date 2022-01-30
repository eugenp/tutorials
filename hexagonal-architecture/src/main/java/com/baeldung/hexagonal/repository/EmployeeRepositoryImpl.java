package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{
    private static final Map<Long, Employee> employees = new HashMap<>();

    @Override
    public Employee findEmployee(Long id) {
        return employees.get(id);
    }

    @Override
    public Employee createEmployee(Employee e) {
        employees.put(e.getId(), e);
        return e;
    }
}
