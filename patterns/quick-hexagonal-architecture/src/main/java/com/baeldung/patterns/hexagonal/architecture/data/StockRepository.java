package com.baeldung.patterns.hexagonal.architecture.data;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.patterns.hexagonal.architecture.domain.Stock;

public interface StockRepository extends CrudRepository<Stock, String> {
}
