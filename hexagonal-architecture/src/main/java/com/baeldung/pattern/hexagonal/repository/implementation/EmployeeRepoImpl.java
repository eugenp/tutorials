package com.baeldung.pattern.hexagonal.repository.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.pattern.hexagonal.domain.Employee;
import com.baeldung.pattern.hexagonal.repository.EmployeeRepo;
import com.baeldung.pattern.hexagonal.repository.jpa.EmployeeRepository;

@Component
public class EmployeeRepoImpl implements EmployeeRepo {
    
    @Autowired
    EmployeeRepository employeeRepository;
    

    @Override
    public void createEmployee(Employee emp) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Employee findById(Integer id) {
        Optional<Employee> emp = employeeRepository.findById(id); 
        return emp.get();
    }

    @Override
    public boolean deleteById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

}
