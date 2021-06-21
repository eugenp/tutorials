package com.baeldung.hexagonal.application.response;

import com.baeldung.hexagonal.domain.Salary;
import com.baeldung.hexagonal.domain.SalaryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryCalculateResponse {

    private UUID id;
    private SalaryStatus status;
    private BigDecimal totalSalary;
    private Integer month;
    private Integer year;
    private BigDecimal basic;
    private BigDecimal houseRent;
    private BigDecimal medicalAllowance;
    private Long employeeId;

    public SalaryCalculateResponse(Salary salary) {
        this.id = salary.getId();
        this.status = salary.getStatus();
        this.totalSalary = salary.getTotalSalary();
        this.month = salary.getMonth();
        this.year = salary.getYear();
        this.basic = salary.getBasic();
        this.houseRent = salary.getHouseRent();
        this.medicalAllowance = salary.getMedicalAllowance();
        this.employeeId = salary.getEmployeeId();
    }
}
