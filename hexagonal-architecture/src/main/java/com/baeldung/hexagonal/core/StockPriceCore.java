package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.adapters.StockAdapter;
import com.baeldung.hexagonal.ports.StockPort;
import com.baeldung.hexagonal.ports.UserRequestPort;
import com.baeldung.hexagonal.repository.StockFetcher;
import com.baeldung.hexagonal.repository.StockRepository;

/**
 * No framework dependencies
 * Notice that this class implements the Ports end to end.(from left side to right side)
 */
public class StockPriceCore implements StockPort, UserRequestPort {

    private StockAdapter stockAdapter;
    private ProfitCalculator profitCalculator;

    public StockPriceCore() {
        this.stockAdapter = new StockAdapter(new StockRepository(new StockFetcher()));
        profitCalculator = new ProfitCalculator();
    }

    public int getBestPossibleProfit(String stockName) {
        return profitCalculator.maxProfit(stockAdapter.getStockPrices(stockName));
    }

    @Override
    public int[] getStockPrices(String stockName) {
        return stockAdapter.getStockPrices(stockName);
    }

    @Override
    public String calculateBestProfitForStock(String stockName) {
        Integer profit = profitCalculator.maxProfit(stockAdapter.getStockPrices(stockName));
        return profit.toString();
    }

    @Override
    public int[] requestStockPrices(String stockName) {
        return stockAdapter.getStockPrices(stockName);
    }
}
