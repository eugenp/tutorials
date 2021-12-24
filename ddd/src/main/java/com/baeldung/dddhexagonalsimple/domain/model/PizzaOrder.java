package com.baeldung.dddhexagonalsimple.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class PizzaOrder {

    private final long id;

    private final Pizza pizza;

    private final int diameterInInches;

    private final BigDecimal price;
}
