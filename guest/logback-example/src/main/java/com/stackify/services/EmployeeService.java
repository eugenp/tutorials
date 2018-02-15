package com.stackify.services;

import com.stackify.models.Employee;

public class EmployeeService {

    public double calculateBonus(Employee user) {
        return 0.1 * user.getSalary();
    }

}
