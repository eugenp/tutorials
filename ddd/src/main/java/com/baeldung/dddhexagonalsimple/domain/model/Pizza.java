package com.baeldung.dddhexagonalsimple.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class Pizza {

    private final long id;

    private final String name;

    private final BigDecimal pricePerSquareInch;
}
