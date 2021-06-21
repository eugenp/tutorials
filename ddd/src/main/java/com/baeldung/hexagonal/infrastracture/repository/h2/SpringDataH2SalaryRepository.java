package com.baeldung.hexagonal.infrastracture.repository.h2;

import com.baeldung.hexagonal.domain.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataH2SalaryRepository  extends JpaRepository<SalaryEntity, UUID> {

    Optional<SalaryEntity> findByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year);

}
