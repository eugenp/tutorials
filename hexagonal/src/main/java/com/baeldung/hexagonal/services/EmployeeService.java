package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.api.EmployeeApi;
import com.baeldung.hexagonal.model.Employee;
import com.baeldung.hexagonal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeApi {

    @Autowired
    private EmployeeRepository employeeRepository ;

    @Override
    public Employee create(String nameFirst, String nameLast) {
        Employee employee = new Employee();
        employee.setNameFirst(nameFirst);
        employee.setNameLast(nameLast);
        return employeeRepository.save(employee) ;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll() ;
    }
}
