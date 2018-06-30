package com.stock.prices.stockprices.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.stock.prices.stockprices.model.*;

@Repository
public interface StockPriceRepository extends ReactiveMongoRepository<StockPrice, String> {

}
