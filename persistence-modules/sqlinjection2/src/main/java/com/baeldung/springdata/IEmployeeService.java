package com.baeldung.springdata;

import java.util.List;

import com.baeldung.model.Employee;

public interface IEmployeeService {
    public List<Employee> searchEmployee(String name);
}
