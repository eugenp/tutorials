package com.baeldung.dddhexagonalsimple.adapters.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pizza.domain.model.Pizza;
import pizza.domain.ports.out.FindPizzaPort;
import pizza.domain.ports.out.SavePizzaPort;

import java.util.Optional;

@Component
@AllArgsConstructor
public class PizzaRepository implements SavePizzaPort, FindPizzaPort {

    private final PizzaCrudRepository repository;

    @Override
    public Pizza save(Pizza pizza) {
        PizzaEntity entity = PizzaEntity.fromDomain(pizza);
        PizzaEntity saved = repository.save(entity);

        return saved.toDomain();
    }

    @Override
    public Optional<Pizza> find(Long id) {
        Optional<PizzaEntity> foundOptional = repository.findById(id);

        return foundOptional.map(PizzaEntity::toDomain);
    }
}
