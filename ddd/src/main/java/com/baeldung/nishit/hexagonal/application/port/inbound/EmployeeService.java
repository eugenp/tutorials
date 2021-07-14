package com.baeldung.nishit.hexagonal.application.port.inbound;

import com.baeldung.nishit.hexagonal.domain.model.Employee;

public interface EmployeeService {
    public Employee addEmployee(String name, int age);

    public Employee getEmployee(String id);

    public Employee deleteEmployee(String id);

}
