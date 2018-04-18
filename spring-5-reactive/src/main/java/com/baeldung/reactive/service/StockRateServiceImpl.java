package com.baeldung.reactive.service;

import java.util.Date;
import java.util.SplittableRandom;

import org.springframework.stereotype.Component;

import com.baeldung.reactive.model.Stock;

@Component("stockRateService")
public class StockRateServiceImpl implements StockRateService {

	@Override
	public Stock getLiveStockRates(String stockName) {

		double stockRate = 0;
		
		switch (stockName) {
		case "APPL":
			stockRate = new SplittableRandom().nextDouble(170.00, 175.99);
			return new Stock(stockName, stockRate, new Date());

		case "GOOGL":
			stockRate = new SplittableRandom().nextDouble(1030.00, 1035.99);
			return new Stock(stockName, stockRate, new Date());

		default:
			stockRate = new SplittableRandom().nextDouble(1, 100);
			return new Stock(stockName, stockRate, new Date());

		}
	}

}
