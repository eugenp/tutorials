package com.baeldung.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.boot.domain.Employee;

/**
 * @author harshavs
 * @since 2019-08-01
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
