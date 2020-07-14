package com.baeldung.hexagonal.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * User: raychenon
 * Date: 14/7/2020
 */
public class StockRepository {

    private Map<String, int[]> cache;
    private StockFetcher stockFetcher;

    public StockRepository(StockFetcher stockFetcher) {
        this.stockFetcher = stockFetcher;
        cache = new HashMap<>();
    }

    public int[] getStock(String stockName) {
        if (cache.containsKey(stockName)) {
            return cache.get(stockName);
        } else {
            int[] prices = stockFetcher.getStock(stockName);
            cache.put(stockName, prices);
            return prices;
        }
    }
}
