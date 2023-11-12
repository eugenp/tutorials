package com.baeldung.spring.data.persistence.springdatajpadifference.springdata.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.persistence.springdatajpadifference.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByFirstName(String firstName);

    @Query(value = "SELECT e FROM Employee e")
    List<Employee> findAllEmployee(Sort sort);
}