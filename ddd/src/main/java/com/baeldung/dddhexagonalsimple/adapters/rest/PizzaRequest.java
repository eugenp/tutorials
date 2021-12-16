package com.baeldung.dddhexagonalsimple.adapters.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
final class PizzaRequest {

    private String name;

    private BigDecimal pricePerSquareInch;
}
