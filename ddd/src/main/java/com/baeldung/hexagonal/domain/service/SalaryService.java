package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.application.request.SalaryCalculateRequest;
import com.baeldung.hexagonal.application.request.SalaryDisburseRequest;
import com.baeldung.hexagonal.application.response.SalaryCalculateResponse;

public interface SalaryService {

    public SalaryCalculateResponse calculateSalary(SalaryCalculateRequest calculateRequest, Long employeeId);

    public void disburseSalary(SalaryDisburseRequest disburseRequest, Long employeeId);

}
