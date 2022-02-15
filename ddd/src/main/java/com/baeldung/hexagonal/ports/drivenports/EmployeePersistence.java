package com.baeldung.hexagonal.ports.drivenports;

import com.baeldung.hexagonal.application.entities.Employee;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public interface EmployeePersistence {
    String saveEmployee(Employee employee);
}
