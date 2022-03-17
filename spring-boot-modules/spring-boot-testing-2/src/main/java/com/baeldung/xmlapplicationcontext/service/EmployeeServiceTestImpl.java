package com.baeldung.xmlapplicationcontext.service;

import com.baeldung.xmlapplicationcontext.domain.Employee;

public class EmployeeServiceTestImpl implements EmployeeService {

    @Override
    public Employee getEmployee() {
        return new Employee("Baeldung-Test", "Admin");
    }
}
