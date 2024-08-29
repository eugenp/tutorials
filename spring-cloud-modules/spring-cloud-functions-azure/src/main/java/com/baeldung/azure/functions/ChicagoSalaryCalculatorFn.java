package com.baeldung.azure.functions;

import java.util.function.Function;

import com.baeldung.azure.functions.entity.Employee;

public class ChicagoSalaryCalculatorFn implements Function<Employee, Employee> {
    @Override
    public Employee apply(Employee employee) {
        Integer finalSalary = employee.getSalary() + 5000;
        employee.setSalary(finalSalary);
        return employee;
    }
}
