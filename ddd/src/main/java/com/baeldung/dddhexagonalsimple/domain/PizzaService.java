package com.baeldung.dddhexagonalsimple.domain;

import lombok.AllArgsConstructor;
import pizza.domain.model.Pizza;
import pizza.domain.ports.in.CreatePizzaUseCase;
import pizza.domain.ports.in.GetPizzaUseCase;
import pizza.domain.ports.out.FindPizzaPort;
import pizza.domain.ports.out.SavePizzaPort;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class PizzaService implements CreatePizzaUseCase, GetPizzaUseCase {

    private final SavePizzaPort savePizzaPort;
    private final FindPizzaPort findPizzaPort;

    @Override
    public Pizza create(String name, BigDecimal pricePerSquareInch) {
        Pizza created = Pizza.builder().name(name).pricePerSquareInch(pricePerSquareInch).build();

        return savePizzaPort.save(created);
    }

    @Override
    public Optional<Pizza> get(Long id) {
        return findPizzaPort.find(id);
    }
}
