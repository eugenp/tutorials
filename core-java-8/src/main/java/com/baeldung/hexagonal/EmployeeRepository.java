package com.baeldung.hexagonal;

import java.util.Optional;

/**
 * Port (interface) that defines an employee's repository contract.
 */
public interface EmployeeRepository {

    /**
     * Saves an employee.
     *
     * @param employee Employee to be saved.
     */
    void save(Employee employee);

    /**
     * Finds an employee.
     *
     * @param employeeId Id to search for.
     * @return The found employee.
     */
    Optional<Employee> findById(Integer employeeId);

}
