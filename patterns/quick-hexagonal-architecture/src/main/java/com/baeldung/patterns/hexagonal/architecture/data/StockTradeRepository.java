package com.baeldung.patterns.hexagonal.architecture.data;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.patterns.hexagonal.architecture.domain.StockTrade;

public interface StockTradeRepository extends CrudRepository<StockTrade, Long> {
}
