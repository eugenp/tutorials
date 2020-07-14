package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.adapters.BestTimetoBuyandSellStockAdapter;
import com.baeldung.hexagonal.repository.StockFetcher;
import com.baeldung.hexagonal.repository.StockRepository;

/**
 * User: raychenon
 * Date: 14/7/2020
 */
public class StockPriceCore {

    private StockRepository stockRepository;
    private BestTimetoBuyandSellStockAdapter adapter;

    public StockPriceCore() {
        this.stockRepository = new StockRepository(new StockFetcher());
        this.adapter = new BestTimetoBuyandSellStockAdapter();
    }

    public int getBestPossibleProfit(String stockName) {
        return adapter.maxProfit(stockRepository.getStock(stockName));
    }

    public int[] getStockPrices(String stockName) {
        return stockRepository.getStock(stockName);
    }
}
