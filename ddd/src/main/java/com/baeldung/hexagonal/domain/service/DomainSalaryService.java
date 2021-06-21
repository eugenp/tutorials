package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.application.request.SalaryCalculateRequest;
import com.baeldung.hexagonal.application.request.SalaryDisburseRequest;
import com.baeldung.hexagonal.application.response.SalaryCalculateResponse;
import com.baeldung.hexagonal.domain.Salary;
import com.baeldung.hexagonal.domain.repository.SalaryRepository;

import java.util.UUID;


public class DomainSalaryService implements SalaryService{

    private final SalaryRepository salaryRepository;

    public DomainSalaryService(final SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public SalaryCalculateResponse calculateSalary(SalaryCalculateRequest calculateRequest, Long employeeId) {
        Salary salary = new Salary(UUID.randomUUID(), calculateRequest.getMonth(), calculateRequest.getYear(), calculateRequest.getBasic(), employeeId);
        salary.calculateSalary();
        salaryRepository.save(salary);
        return new SalaryCalculateResponse(salary);
    }

    @Override
    public void disburseSalary(SalaryDisburseRequest disburseRequest, Long employeeId) {
        Salary salary = this.getSalaryEntity(disburseRequest.getMonth(), disburseRequest.getYear(), employeeId);
        salary.disburseSalary();
        salaryRepository.save(salary);
    }

    private Salary getSalaryEntity(Integer month, Integer year, Long employeeId) {
        return salaryRepository
                .findByEmployeeIdAndMonthAndYear(employeeId, month, year)
                .orElseThrow(() -> new RuntimeException(
                        "Salary with given employee id, month and year does not exist!"));
    }
}

