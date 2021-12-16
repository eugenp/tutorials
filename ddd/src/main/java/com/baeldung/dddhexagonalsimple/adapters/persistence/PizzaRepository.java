package com.baeldung.dddhexagonalsimple.adapters.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.baeldung.dddhexagonalsimple.domain.model.Pizza;
import com.baeldung.dddhexagonalsimple.domain.ports.outbound.FindPizzaPort;
import com.baeldung.dddhexagonalsimple.domain.ports.outbound.SavePizzaPort;

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
