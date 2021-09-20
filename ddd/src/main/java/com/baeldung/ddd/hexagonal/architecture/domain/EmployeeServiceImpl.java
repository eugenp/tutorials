package com.baeldung.ddd.hexagonal.architecture.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.ddd.hexagonal.architecture.input.port.EmployeeService;
import com.baeldung.ddd.hexagonal.architecture.output.port.EmployeeDatabaseService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    
    EmployeeDatabaseService empDatabaseSvc;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeDatabaseService empDatabaseSvc) {
        this.empDatabaseSvc = empDatabaseSvc;
    }

    @Override
    public String saveEmployeeDetails(Employee e) {
        return empDatabaseSvc.saveEmployee(e); 
    }

    @Override
    public Employee getEmployeeDetailsById(String id) {
        return empDatabaseSvc.getEmployee(id);
    }

    @Override
    public void deleteEmployeeDetailsById(String id) {
        empDatabaseSvc.deleteEmployee(id);
    }
    
    @Override
    public void updateEmployeeDetailsById(Employee e) {
        empDatabaseSvc.updateEmployee(e);
    }

}
