package com.baeldung.hexgonal.services;


import com.baeldung.hexgonal.repo.EmployeeRepository;

public class EmployeeServiceFactory {

    public static EmployeeService getInstance(EmployeeRepository employeeRepository) {
        return new EmployeeServiceImpl(employeeRepository);
    }

}
