package com.baeldung.dddhexagonalsimple.domain.ports.outbound;

import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;

public interface SavePizzaOrderPort {
    PizzaOrder save(PizzaOrder pizzaOrder);
}
