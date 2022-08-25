package com.baeldung.repository;

import com.baeldung.model.Employee;
import ratpack.exec.Promise;

import java.util.HashMap;
import java.util.Map;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final Map<Long, Employee> EMPLOYEE_MAP = new HashMap<>();

    public EmployeeRepositoryImpl() {
        EMPLOYEE_MAP.put(1L, new Employee(1L, "Ms", "Jane Doe"));
        EMPLOYEE_MAP.put(2L, new Employee(2L, "Mr", "NY"));
    }

    @Override
    public Promise<Employee> findEmployeeById(Long id) throws Exception {
        return Promise.async(downstream -> {
            Thread.sleep(500);
            downstream.success(EMPLOYEE_MAP.get(id));
        });
    }
}
