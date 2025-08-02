package com.baeldung.kogito.rules.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Conditions {

    @Min(300)
    @Max(850)
    private int creditScore;
    @PositiveOrZero
    private double income;
    @PositiveOrZero
    private double debt;

}
