package com.baeldung.hexagonal.application.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDisburseRequest {
    Integer month;
    Integer year;
}
