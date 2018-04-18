package com.baeldung.reactive.service;

import com.baeldung.reactive.model.Stock;

public interface StockRateService {

        public Stock getLiveStockRates(String stockName);

}

