package com.stackify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackify.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
