package com.baeldung.dddhexagonalsimple.domain.ports.inbound;

import com.baeldung.dddhexagonalsimple.domain.model.Pizza;
import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;

public interface ProcessPizzaOrderUseCase {
    PizzaOrder process(Pizza pizza, Integer diameterInInches);
}
