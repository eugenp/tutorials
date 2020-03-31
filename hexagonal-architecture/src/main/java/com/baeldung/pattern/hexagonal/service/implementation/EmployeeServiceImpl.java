package com.baeldung.pattern.hexagonal.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.pattern.hexagonal.domain.Employee;
import com.baeldung.pattern.hexagonal.exception.ResourceNotFoundException;
import com.baeldung.pattern.hexagonal.repository.EmployeeRepo;
import com.baeldung.pattern.hexagonal.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo empRepo;

    @Override
    public Employee createEmployee(Employee emp) {
        return empRepo.createEmployee(emp);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Employee emp = empRepo.findById(id);
        if (null == emp) {
            throw new ResourceNotFoundException("EMP_NOT_FOUND", "Employee not found");
        }
        return emp;
    }

    @Override
    public void removeEmployeeById(Integer id) {
        empRepo.deleteById(id);
    }

}
