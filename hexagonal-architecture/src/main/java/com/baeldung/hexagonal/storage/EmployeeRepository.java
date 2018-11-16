package com.baeldung.hexagonal.storage;

import java.util.List;

import com.baeldung.hexagonal.domain.Employee;

public interface EmployeeRepository {
   public Long save(Employee employee);
   public Employee findById(Long id);
   public List<Employee> findAll();
}
