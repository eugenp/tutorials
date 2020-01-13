package com.baeldung.hexagonalarchitecturejava.repository;

import com.baeldung.hexagonalarchitecturejava.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>
{
    @Override
    Optional<Employee> findById(Long integer);
}
