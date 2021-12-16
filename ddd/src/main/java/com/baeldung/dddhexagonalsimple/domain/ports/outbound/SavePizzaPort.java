package com.baeldung.dddhexagonalsimple.domain.ports.outbound;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;

public interface SavePizzaPort {
    Pizza save(Pizza pizza);
}
