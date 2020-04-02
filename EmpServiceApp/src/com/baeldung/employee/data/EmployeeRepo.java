package com.baeldung.employee.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.employee.domain.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
