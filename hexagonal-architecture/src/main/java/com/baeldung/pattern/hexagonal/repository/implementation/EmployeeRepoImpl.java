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
    public Employee createEmployee(Employee emp) {
       Employee newEmp = employeeRepository.save(emp);
       return newEmp;
    }

    @Override
    public Employee findById(Integer id) {
        Optional<Employee> emp = employeeRepository.findById(id); 
        return emp.get();
    }

    @Override
    public void deleteById(Integer id) {
        if(null != findById(id)) {
            employeeRepository.deleteById(id);
        }
    }

}
