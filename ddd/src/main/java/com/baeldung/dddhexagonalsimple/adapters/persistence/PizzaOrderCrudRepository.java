package com.baeldung.dddhexagonalsimple.adapters.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PizzaOrderCrudRepository extends CrudRepository<PizzaOrderEntity, Long> {
}
