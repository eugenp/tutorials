package com.baeldung.dddhexagonalsimple.domain.ports.inbound;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;

import java.util.Optional;

public interface GetPizzaUseCase {
    Optional<Pizza> get(Long id);
}
