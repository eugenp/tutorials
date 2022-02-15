package com.baeldung.hexagonaljava.ports.drivenports;

import com.baeldung.hexagonaljava.application.entities.Employee;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public interface EmployeePersistence {
    String saveEmployee(Employee employee);
}
