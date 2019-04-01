package com.baeldung.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
