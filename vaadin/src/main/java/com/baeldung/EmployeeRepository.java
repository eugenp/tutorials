package com.baeldung;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
