package com.baeldung.azure.functions;

import java.util.function.Function;

import com.baeldung.azure.functions.entity.Employee;

public class EmployeeSalaryFunction implements Function<Employee, Employee> {

    @Override
    public Employee apply(Employee employee) {
        int allowance;
        switch (employee.getCity()) {
            case "Chicago" -> allowance = 5000;
            case "California" -> allowance = 2000;
            case "New York" -> allowance = 2500;
            default -> allowance = 1000;
        }
        int finalSalary = employee.getSalary() + allowance;
        employee.setSalary(finalSalary);
        return employee;
    }
}
