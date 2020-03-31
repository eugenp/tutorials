package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.domain.Employee;

public interface EmployeeRepo {

    public void createEmployee(Employee emp);

    public Employee findById(Integer id);
    
    public boolean deleteById(Integer id);

}
