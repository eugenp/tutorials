package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoDBRepository implements EmployeeRepository {

    @Autowired
    MongoRepoEx mongoRepository;

    @Override
    public Employee add(Employee employee) {
        return mongoRepository.insert(employee);
    }

    @Override
    public Optional<Employee> findById(String id) {
        return mongoRepository.findById(id);
    }
}
