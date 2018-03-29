package com.baeldung.stockexchange.services;

import com.baeldung.stockexchange.models.Stock;
import com.baeldung.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockService
{
	@Autowired
	private StockRepository stockRepository;

	public Mono<Stock> createStock(Stock stock)
	{
		return stockRepository.insert(stock);
	}

	public Mono<Stock> updateStock(Stock stock)
	{
		return stockRepository.insert(stock);
	}

	public Mono<Stock> getStock(String stockId)
	{
		return stockRepository.findById(stockId);
	}

	public Flux<Stock> getAllStocks()
	{
		return stockRepository.findAll();
	}

	public Mono<Void> deleteStock(String stockId)
	{
		return stockRepository.deleteById(stockId);
	}
}
