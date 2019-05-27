package com.baeldung.hexagonal.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baeldung.hexagonal.entity.Employee;
import com.baeldung.hexagonal.service.EmployeePort;

public class EmployeeAdapter implements EmployeePort {

    public List<Employee> getEmployees() {
        return Arrays.asList(new Employee(1L, "Vinod"), new Employee(2L, "Kumar"), new Employee(3L, "Kashyap"), new Employee(4L, "Eugen"), new Employee(5L, "Baeldung"));
    }

}
