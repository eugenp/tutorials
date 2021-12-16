package com.baeldung.dddhexagonalsimple.domain.ports.outbound;

import pizza.domain.model.Pizza;

public interface SavePizzaPort {
    Pizza save(Pizza pizza);
}
