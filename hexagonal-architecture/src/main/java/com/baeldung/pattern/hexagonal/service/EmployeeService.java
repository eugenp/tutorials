package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.domain.Employee;

public interface EmployeeService {

        public Employee createEmployee(Employee emp);

        public Employee getEmployeeById(Integer id);

        public void removeEmployeeById(Integer id);

}
