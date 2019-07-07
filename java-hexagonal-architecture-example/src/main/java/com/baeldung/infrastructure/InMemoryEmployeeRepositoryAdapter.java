package com.baeldung.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.domain.Employee;
import com.baeldung.domain.EmployeeRepositoryPort;

@Repository
public class InMemoryEmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private Map<String, Employee> employeeRepository = new HashMap<>();

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employeeRepository.values());
    }

    @Override
    public Optional<Employee> findById(String employeeId) {
        if (employeeRepository.containsKey(employeeId)) {
            return Optional.of(employeeRepository.get(employeeId));
        }
        return Optional.empty();
    }

    @Override
    public Employee save(Employee employee) {
        employeeRepository.put(employee.getEmployeeId(), employee);
        return employee;
    }
}
