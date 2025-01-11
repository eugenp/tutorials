package com.baeldung.azure.functions;

import java.util.function.Function;

import com.baeldung.azure.functions.entity.Employee;

public class NewYorkSalaryCalculatorFn implements Function<Employee, Employee> {
    @Override
    public Employee apply(Employee employee) {
        Integer finalSalary = employee.getSalary() + 2500;
        employee.setSalary(finalSalary);
        return employee;
    }

}
