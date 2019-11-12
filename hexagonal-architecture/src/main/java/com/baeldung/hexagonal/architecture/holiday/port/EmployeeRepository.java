package com.baeldung.hexagonal.architecture.holiday.port;

import com.baeldung.hexagonal.architecture.holiday.core.domain.Employee;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {

    Optional<Employee> getInformationAboutEmployee(UUID employeeId);
}
