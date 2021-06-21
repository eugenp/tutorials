package com.baeldung.hexagonal.application.controller;

import com.baeldung.hexagonal.application.request.SalaryCalculateRequest;
import com.baeldung.hexagonal.application.request.SalaryDisburseRequest;
import com.baeldung.hexagonal.application.response.SalaryCalculateResponse;
import com.baeldung.hexagonal.domain.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequestMapping("/v1/salary")
public class SalaryController {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping(value = "/{employeeId}/calculate", consumes = MediaType.APPLICATION_JSON_VALUE)
    SalaryCalculateResponse calculateSalary(@PathVariable final Long employeeId, @Valid @RequestBody final SalaryCalculateRequest calculateRequest) {
        return salaryService.calculateSalary(calculateRequest, employeeId);
    }

    @PostMapping("/{employeeId}/disburse")
    void disburseSalary(@PathVariable final Long employeeId, SalaryDisburseRequest disburseRequest) {
        salaryService.disburseSalary(disburseRequest, employeeId);
    }
}
