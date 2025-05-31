package com.baeldung.kogito.boundary.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionsDto {

    @Min(300)
    @Max(850)
    private int creditScore;
    @PositiveOrZero
    private double income;
    @PositiveOrZero
    private double debt;

}
