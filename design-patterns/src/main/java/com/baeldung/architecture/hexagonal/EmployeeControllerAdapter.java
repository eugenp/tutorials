package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.model.Employee;
import com.baeldung.architecture.hexagonal.service.EmployeeService;

import java.util.List;

public class EmployeeControllerAdapter {

    private EmployeeService employeeService;

    public EmployeeControllerAdapter(EmployeeService employeeService){
        this.employeeService = employeeService ;

    }

    public void createEmployee(Employee employee) {
        employeeService.createEmployee(employee);

    }

    public List<Employee> getEmployeeList() {
        return employeeService.getEmployeeList();
    }
}
