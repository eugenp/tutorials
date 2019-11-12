package com.baeldung.hexagonal.architecture.holiday.adapter;

import com.baeldung.hexagonal.architecture.holiday.core.domain.Employee;
import com.baeldung.hexagonal.architecture.holiday.port.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final ConcurrentHashMap<UUID, Employee> employees = new ConcurrentHashMap<>();

    @Override
    public Optional<Employee> getInformationAboutEmployee(UUID employeeId) {
        return Optional.ofNullable(employees.get(employeeId));
    }
}
