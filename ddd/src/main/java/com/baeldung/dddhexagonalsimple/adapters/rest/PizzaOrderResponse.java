package com.baeldung.dddhexagonalsimple.adapters.rest;

import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
final class PizzaOrderResponse {

    private final long id;

    private final PizzaResponse pizza;

    private final int diameterInInches;

    private final BigDecimal price;

    public static PizzaOrderResponse fromDomain(PizzaOrder domainObject) {
        return PizzaOrderResponse.builder().id(domainObject.getId()).pizza(PizzaResponse.fromDomain(domainObject.getPizza())).diameterInInches(domainObject.getDiameterInInches()).price(domainObject.getPrice()).build();
    }
}
