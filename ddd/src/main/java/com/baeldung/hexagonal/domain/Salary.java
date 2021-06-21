package com.baeldung.hexagonal.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Salary {

    private final BigDecimal houseRestInPercent = new BigDecimal(0.15);
    private final BigDecimal medicalAllowanceInPercent = new BigDecimal(0.10);

    private UUID id;
    private SalaryStatus status;
    private BigDecimal totalSalary;
    private Integer month;
    private Integer year;
    private BigDecimal basic;
    private BigDecimal houseRent;
    private BigDecimal medicalAllowance;
    private Long employeeId;

    public Salary(){
    }

    public Salary(final UUID id, Integer month, Integer year, BigDecimal basic, Long employeeId){
        this.id = id;
        this.month = month;
        this.year = year;
        this.basic = basic;
        this.employeeId = employeeId;
    }

    public Salary(UUID id, SalaryStatus status, BigDecimal totalSalary, Integer month, Integer year, BigDecimal basic, BigDecimal houseRent, BigDecimal medicalAllowance, Long employeeId) {
        this.id = id;
        this.status = status;
        this.totalSalary = totalSalary;
        this.month = month;
        this.year = year;
        this.basic = basic;
        this.houseRent = houseRent;
        this.medicalAllowance = medicalAllowance;
        this.employeeId = employeeId;
    }

    public void calculateSalary() {
        this.status = SalaryStatus.CALCULATED;
        this.houseRent = this.basic.multiply(houseRestInPercent);
        this.medicalAllowance = this.basic.multiply(medicalAllowanceInPercent);
        this.totalSalary = basic.add(houseRent).add(medicalAllowance);
    }

    public void disburseSalary() {
        this.validateIsStatusCalculate();
        this.status = SalaryStatus.DISBURSED;
    }

    private void validateIsStatusCalculate() {
        if (SalaryStatus.CALCULATED.equals(this.status)) {
            throw new DomainException("The salary is not calculated.");
        }
    }

}
