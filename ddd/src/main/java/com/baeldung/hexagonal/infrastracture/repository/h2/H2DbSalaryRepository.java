package com.baeldung.hexagonal.infrastracture.repository.h2;

import com.baeldung.hexagonal.domain.Salary;
import com.baeldung.hexagonal.domain.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class H2DbSalaryRepository implements SalaryRepository {

    private final SpringDataH2SalaryRepository salaryRepository;

    @Autowired
    public H2DbSalaryRepository(SpringDataH2SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public Optional<Salary> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Salary> findByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year) {
        Optional<SalaryEntity> salaryEntity = salaryRepository.findByEmployeeIdAndMonthAndYear(employeeId, month, year);
        if (salaryEntity.isPresent()) {
            return Optional.of(salaryEntity.get()
                    .toSalary());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(Salary salary) {
        salaryRepository.save(new SalaryEntity(salary));
    }
}
