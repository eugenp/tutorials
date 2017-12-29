package com.baeldung.spring.core.di;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class StockService {

    // List of available stock
    private Map<Integer, Stock> stockMap = new HashMap<Integer, Stock>();

    public void addStock(Stock stock) {
        stockMap.put(stock.getId(), stock);
    }

    public Stock getStock(int stockId) {
        return stockMap.get(stockId);
    }

}
