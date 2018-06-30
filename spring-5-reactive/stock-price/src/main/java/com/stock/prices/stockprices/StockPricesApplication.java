package com.stock.prices.stockprices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class StockPricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPricesApplication.class, args);
	}
}
