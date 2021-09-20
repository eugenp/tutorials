package com.baeldung.ddd.hexagonal.architecture.input.port;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;

public interface EmployeeService {

    public String saveEmployeeDetails(Employee e);
    public Employee getEmployeeDetailsById(String id);
    public void deleteEmployeeDetailsById(String id);
    public void updateEmployeeDetailsById(Employee e);
}
