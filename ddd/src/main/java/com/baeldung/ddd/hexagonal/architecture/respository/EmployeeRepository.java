package com.baeldung.ddd.hexagonal.architecture.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.ddd.hexagonal.architecture.domain.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String>{

}
