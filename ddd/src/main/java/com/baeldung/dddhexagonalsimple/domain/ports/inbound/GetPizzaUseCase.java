package com.baeldung.dddhexagonalsimple.domain.ports.inbound;

import pizza.domain.model.Pizza;

import java.util.Optional;

public interface GetPizzaUseCase {
    Optional<Pizza> get(Long id);
}
