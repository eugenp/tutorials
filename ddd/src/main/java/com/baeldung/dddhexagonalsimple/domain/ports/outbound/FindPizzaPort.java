package com.baeldung.dddhexagonalsimple.domain.ports.outbound;

import pizza.domain.model.Pizza;

import java.util.Optional;

public interface FindPizzaPort {
    Optional<Pizza> find(Long id);
}
