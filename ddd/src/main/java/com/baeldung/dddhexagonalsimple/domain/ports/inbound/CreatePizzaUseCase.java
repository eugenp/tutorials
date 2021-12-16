package com.baeldung.dddhexagonalsimple.domain.ports.inbound;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;

import java.math.BigDecimal;

public interface CreatePizzaUseCase {
    Pizza create(String name, BigDecimal pricePerSquareInch);
}
