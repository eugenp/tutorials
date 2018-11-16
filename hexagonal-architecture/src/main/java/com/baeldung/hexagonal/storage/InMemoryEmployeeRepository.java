package com.baeldung.hexagonal.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.hexagonal.domain.Employee;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static Map<Long, Employee> employees = new HashMap<>();

    @Override
    public Long save(Employee employee) {
        Long id = employee.getId();
        employees.put(id, employee);
        return id;
    }

    @Override
    public Employee findById(Long id) {
        return employees.get(id);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> all = new ArrayList<Employee>(employees.values());
        return all;
    }

}
