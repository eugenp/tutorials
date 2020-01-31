package com.baeldung.hexagonal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Main class, crosses boundaries through the use of Ports and Adapters.
 */
public class EmployeeCentral {

    private static final Logger log = LoggerFactory.getLogger(EmployeeCentral.class);
    private EmployeeRepository employeeRepository;

    public EmployeeCentral(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private void createEmployeeIfNotExists(Employee employeeToCreateIfNotExists) {
        EmployeeCentral employeeCentral = new EmployeeCentral(new EmployeeRepositoryInMemoryImpl());
        Optional<Employee> employee = employeeRepository.findById(employeeToCreateIfNotExists.getId());
        if (employee.isPresent()) {
            log.info("Employee ID={} exists, and it's named {}", employee.get().getId(), employee.get().getName());
        } else {
            log.info("Employee ID={} not found, creating...", employeeToCreateIfNotExists.getId());
            employeeRepository.save(employeeToCreateIfNotExists);
            log.info("Employee created successfully");
        }
    }

}
