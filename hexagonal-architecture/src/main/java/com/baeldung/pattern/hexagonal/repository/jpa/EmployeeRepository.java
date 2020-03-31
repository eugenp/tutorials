package com.baeldung.pattern.hexagonal.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal.domain.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

     
}
