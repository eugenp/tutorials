package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.domain.Employee;

public interface EmployeeRepo {

    public Employee createEmployee(Employee emp);

    public Employee findById(Integer id);
    
    public void deleteById(Integer id);

}
