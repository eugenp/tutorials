package com.baeldung.dddhexagonalsimple.adapters.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PizzaCrudRepository extends CrudRepository<PizzaEntity, Long> {
}
