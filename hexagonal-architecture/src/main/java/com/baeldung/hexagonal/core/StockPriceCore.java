package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.repository.StockFetcher;
import com.baeldung.hexagonal.repository.StockRepository;

/**
 * No framework dependencies
 */
public class StockPriceCore {

    private StockRepository stockRepository;
    private ProfitCalculator profitCalculator;

    public StockPriceCore() {
        this.stockRepository = new StockRepository(new StockFetcher());
        profitCalculator = new ProfitCalculator();
    }

    public int getBestPossibleProfit(String stockName) {
        return profitCalculator.maxProfit(stockRepository.getStock(stockName));
    }

    public int[] getStockPrices(String stockName) {
        return stockRepository.getStock(stockName);
    }
}
