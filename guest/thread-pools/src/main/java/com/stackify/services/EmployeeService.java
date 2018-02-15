package com.stackify.services;

import com.stackify.models.Employee;

public class EmployeeService {
    public double calculateBonus(Employee employee) {
        return 0.1 * employee.getSalary();
    }
}
