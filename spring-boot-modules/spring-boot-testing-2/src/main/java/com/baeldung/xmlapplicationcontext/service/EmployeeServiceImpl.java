package com.baeldung.xmlapplicationcontext.service;

import com.baeldung.xmlapplicationcontext.domain.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee getEmployee() {
        return new Employee("Baeldung", "Admin");
    }
}
