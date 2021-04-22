package com.baeldung.pattern.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private static final BigDecimal BONUS_FACTOR = new BigDecimal("0.1");

    private String employeeId;
    private String department;
    private int salary;
    private BigDecimal bonus;

    public void calculateBonus() {
        if (department.equals("IT")) {
            bonus = BONUS_FACTOR.multiply(new BigDecimal(salary));
        } else {
            bonus = BigDecimal.ZERO;
        }
    }
}
