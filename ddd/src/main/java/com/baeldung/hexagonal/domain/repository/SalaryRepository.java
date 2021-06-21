package com.baeldung.hexagonal.domain.repository;

import com.baeldung.hexagonal.domain.Salary;

import java.util.Optional;
import java.util.UUID;

public interface SalaryRepository {

    Optional<Salary> findById(UUID id);

    Optional<Salary> findByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year);

    void save(Salary salary);
}
