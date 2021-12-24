package com.baeldung.dddhexagonalsimple.adapters.persistence;

import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;
import com.baeldung.dddhexagonalsimple.domain.ports.outbound.SavePizzaOrderPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PizzaOrderRepository implements SavePizzaOrderPort {

    private final PizzaOrderCrudRepository repository;

    @Override
    public PizzaOrder save(PizzaOrder pizzaOrder) {
        PizzaOrderEntity entity = PizzaOrderEntity.fromDomain(pizzaOrder);
        PizzaOrderEntity saved = repository.save(entity);

        return saved.toDomain();
    }
}
