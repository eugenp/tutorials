package com.baeldung.ddd.hexagonal.architecture.output.port;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;

public interface EmployeeDatabaseService {

    public String saveEmployee(Employee e);
    public Employee getEmployee(String id);
    public void deleteEmployee(String id);
    public void updateEmployee(Employee e);
}
