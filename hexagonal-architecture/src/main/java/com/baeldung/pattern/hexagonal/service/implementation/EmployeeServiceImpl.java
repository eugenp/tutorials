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
    public void createEmployee(Employee emp) {
        // TODO Auto-generated method stub

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
    public boolean removeEmployeeById(Integer id) {
        // TODO Auto-generated method stub
        return false;
    }

}
