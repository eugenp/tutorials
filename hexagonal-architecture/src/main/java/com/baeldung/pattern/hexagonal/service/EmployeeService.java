package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.domain.Employee;

public interface EmployeeService {
    
    public void createEmployee(Employee emp);
    
    public Employee getEmployeeById(Integer id);
    
    public boolean removeEmployeeById(Integer id);

}
