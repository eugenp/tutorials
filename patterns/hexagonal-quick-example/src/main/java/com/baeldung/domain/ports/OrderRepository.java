package com.baeldung.domain.ports;

import com.baeldung.domain.model.CoffeeOrder;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository {

    int create(CoffeeOrder order);

    Optional<CoffeeOrder> findById(int id);

    Collection<CoffeeOrder> findAll();

    void update(CoffeeOrder order);

}
