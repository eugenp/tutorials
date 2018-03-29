package com.baeldung.stockexchange.repository;

import com.baeldung.stockexchange.models.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StockRepository extends ReactiveMongoRepository<Stock, Long>
{
	public Mono<Stock> findById(String stockId);

	public Mono<Void> deleteById(String stockId);
}
