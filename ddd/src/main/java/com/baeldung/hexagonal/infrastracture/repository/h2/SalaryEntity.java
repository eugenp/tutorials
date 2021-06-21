package com.baeldung.hexagonal.infrastracture.repository.h2;

import com.baeldung.hexagonal.domain.Salary;
import com.baeldung.hexagonal.domain.SalaryStatus;
import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="Salary")
public class SalaryEntity {

    @Id
    @Column(name = "Id", updatable = false, nullable = false)
    private UUID id;

    @Column(name="status")
    private SalaryStatus status;

    @Column(name="totalsalary")
    private BigDecimal totalSalary;

    @Column(name="month")
    private Integer month;

    @Column(name="year")
    private Integer year;

    @Column(name="year")
    private BigDecimal basic;

    @Column(name="houserent")
    private BigDecimal houseRent;

    @Column(name="medicalallowance")
    private BigDecimal medicalAllowance;

    @Column(name="employeeid")
    private Long employeeId;

    public SalaryEntity(Salary salary) {
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

    public Salary toSalary() {
        Salary salary = new Salary(id, status, totalSalary, month, year, basic, houseRent, medicalAllowance, employeeId);
        return salary;
    }
}
