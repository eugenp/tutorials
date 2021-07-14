package com.baeldung.nishit.hexagonal.application.port.outbound;

import com.baeldung.nishit.hexagonal.domain.model.Employee;

public interface EmployeeRepository {
    public Employee addEmployee(Employee e);

    public Employee getEmployee(String id);

    public Employee deleteEmployee(String id);
}
