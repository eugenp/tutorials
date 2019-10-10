package com.baeldung.hexagonal;

import com.baeldung.hexagonal.model.Employee;
import com.baeldung.hexagonal.service.EmployeeService;

import java.util.List;

public class PrimaryAdapter implements InboundPort {

    private EmployeeService employeeService ;

    PrimaryAdapter(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    public void createEmployee(Employee employee) {
        employeeService.createEmployee(employee);

    }

    public List<Employee> getEmployeeList() {
        return employeeService.getEmployeeList();
    }
}
