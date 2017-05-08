package com.baeldung.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
