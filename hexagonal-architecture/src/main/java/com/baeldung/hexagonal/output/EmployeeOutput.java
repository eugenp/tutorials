package com.baeldung.hexagonal.output;

import java.util.List;

import com.baeldung.hexagonal.domain.Employee;

public interface EmployeeOutput {
    public void writeAll(List<Employee> employees);
}
